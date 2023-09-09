package com.ohoon.board.app.security;

import com.ohoon.board.app.exception.MemberNotFoundException;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.app.repository.AuthSocialRepository;
import com.ohoon.board.domain.*;
import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<>() {
    };

    private final Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();

    private final RestOperations restOperations = new RestTemplate();

    private final MemberRepository memberRepository;

    private final AuthSocialRepository authSocialRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = getAttributes(userRequest);
        AuthSocialAttributes authSocialAttributes = AuthSocialAttributes.create(userRequest, attributes);
        if (!authSocialRepository.existsBySubject(authSocialAttributes.getSubject())) {
            joinMember(authSocialAttributes);
        }

        AuthSocial findAuthSocial = authSocialRepository.findBySubject(authSocialAttributes.getSubject())
                .orElseThrow(() -> new AuthenticationServiceException("해당 회원의 인증 정보가 존재하지 않습니다."));
        Member findMember = memberRepository.findById(findAuthSocial.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        return OAuth2Member.create(findMember, findAuthSocial, attributes);
    }

    private Map<String, Object> getAttributes(OAuth2UserRequest userRequest) {
        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
        ResponseEntity<Map<String, Object>> response = getResponse(request);
        return response.getBody();
    }

    private ResponseEntity<Map<String, Object>> getResponse(RequestEntity<?> request) {
        return this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
    }

    private void joinMember(AuthSocialAttributes authSocialAttributes) {
        Member savedMember = memberRepository.save(authSocialAttributes.toMemberEntity());
        authSocialRepository.save(authSocialAttributes.toAuthSocialEntity(savedMember));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class AuthSocialAttributes {

        private String subject;

        private String username;

        private String nickname;

        private String email;

        private ProviderType provider;

        public static AuthSocialAttributes create(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
            ProviderType provider = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName();
            return switch (provider) {
                case GOOGLE -> {
                    String subject = attributes.get(userNameAttributeName).toString();
                    String username = attributes.get("name").toString();
                    String email = attributes.get("email").toString();
                    yield new AuthSocialAttributes(subject, username, null, email, provider);
                }

                case NAVER -> {
                    Map<?, ?> response = (Map<?, ?>) attributes.get("response");
                    String subject = response.get(userNameAttributeName).toString();
                    String username = response.get("name").toString();
                    String nickname = response.get("nickname").toString();
                    String email = response.get("email").toString();
                    yield new AuthSocialAttributes(subject, username, nickname, email, provider);
                }
            };
        }

        public Member toMemberEntity() {
            return Member.create(this.username, this.nickname, this.email, Role.create(RoleType.MEMBER));
        }

        public AuthSocial toAuthSocialEntity(Member member) {
            return AuthSocial.create(this.subject, this.provider, member);
        }
    }
}
