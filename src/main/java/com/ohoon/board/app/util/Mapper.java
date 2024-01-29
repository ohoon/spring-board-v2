package com.ohoon.board.app.util;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.OAuth2MemberService.AuthSocialAttributes;
import com.ohoon.board.domain.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class Mapper {

    public static CurrentMemberDto toCurrentMemberDto(Member member) {
        return new CurrentMemberDto(
                member.getId(),
                member.getName(),
                member.getRoles().stream()
                        .map(Role::getType)
                        .toList());
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getMemberId(),
                comment.getPostId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.isRemoved(),
                comment.getCreatedDate(),
                comment.getChildren().stream()
                        .filter(child -> !child.isRemoved())
                        .sorted(Comparator.comparing(Comment::getId))
                        .map(this::toCommentLeafDto)
                        .toList());
    }

    public CommentLeafDto toCommentLeafDto(Comment comment) {
        return new CommentLeafDto(
                comment.getId(),
                comment.getMemberId(),
                comment.getPostId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.isRemoved(),
                comment.getCreatedDate());
    }

    public Comment toComment(CommentWriteDto commentWriteDto, Member member, Post post) {
        return Comment.create(
                commentWriteDto.getContent(),
                member,
                post);
    }

    public Member toMember(MemberJoinDto memberJoinDto) {
        return Member.create(
                memberJoinDto.getUsername(),
                memberJoinDto.getNickname(),
                memberJoinDto.getEmail(),
                Role.create(RoleType.MEMBER));
    }

    public Member toAdminMember(MemberJoinDto memberJoinDto) {
        return Member.create(
                memberJoinDto.getUsername(),
                memberJoinDto.getNickname(),
                memberJoinDto.getEmail(),
                Role.create(RoleType.ADMIN),
                Role.create(RoleType.MEMBER));
    }

    public AuthPassword toAuthPassword(MemberJoinDto memberJoinDto, Member member) {
        return AuthPassword.create(
                memberJoinDto.getPassword(),
                member);
    }

    public MemberModifyDto toMemberModifyDto(MemberProfileDto memberProfileDto) {
        return new MemberModifyDto(
                memberProfileDto.getUsername(),
                memberProfileDto.getNickname(),
                memberProfileDto.getEmail(),
                memberProfileDto.getRoles(),
                memberProfileDto.getCreatedDate(),
                memberProfileDto.getLastModifiedDate()
        );
    }

    public MemberProfileDto toMemberProfileDto(Member member, boolean isSocial) {
        return new MemberProfileDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                isSocial,
                member.getRoles().stream()
                        .map(Role::getSimpleType)
                        .toList(),
                member.getCreatedDate(),
                member.getLastModifiedDate());
    }

    public MemberListDto toMemberListDto(Member member) {
        return new MemberListDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                String.join(", ", member.getRoles().stream()
                        .map(Role::getSimpleType).toList()));
    }

    public PostEditDto toPostEditDto(PostReadDto postReadDto) {
        return new PostEditDto(
                postReadDto.getTitle(),
                postReadDto.getContent(),
                postReadDto.isNotice()
        );
    }

    public PostListDto toPostListDto(Post post) {
        return new PostListDto(
                post.getId(),
                post.getMemberId(),
                post.getTitle(),
                post.getTotalComments(),
                post.getAuthor(),
                post.getView(),
                post.getTotalVotes(),
                post.getCreatedDate());
    }

    public PostReadDto toPostReadDto(Post post) {
        return new PostReadDto(
                post.isNotice(),
                post.getId(),
                post.getMemberId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getView(),
                post.getTotalVotes(),
                post.getCreatedDate());
    }

    public Post toPost(PostWriteDto postWriteDto, Member member) {
        return Post.create(
                postWriteDto.getTitle(),
                postWriteDto.getContent(),
                postWriteDto.isNotice(),
                member);
    }

    public Member toMember(AuthSocialAttributes authSocialAttributes) {
        return Member.create(
                authSocialAttributes.getUsername(),
                authSocialAttributes.getNickname(),
                authSocialAttributes.getEmail(),
                Role.create(RoleType.MEMBER));
    }

    public AuthSocial toAuthSocial(AuthSocialAttributes authSocialAttributes, Member member) {
        return AuthSocial.create(
                authSocialAttributes.getSubject(),
                authSocialAttributes.getProvider(),
                member);
    }
}
