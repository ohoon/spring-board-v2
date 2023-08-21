package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.PostEditDto;
import com.ohoon.board.app.dto.PostListDto;
import com.ohoon.board.app.dto.PostReadDto;
import com.ohoon.board.app.dto.PostWriteDto;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.app.repository.PostRepository;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Long write(Long memberId, PostWriteDto postWriteDto) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        Post savedPost = postRepository.save(postWriteDto.toEntity(findMember));
        return savedPost.getId();
    }

    public PostReadDto read(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        return PostReadDto.fromEntity(findPost);
    }

    public Page<PostListDto> list(Pageable pageable) {
        return postRepository.list(pageable)
                .map(PostListDto::fromEntity);
    }

    @Transactional
    public void edit(Long postId, PostEditDto postEditDto) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        findPost.edit(postEditDto.getTitle(), postEditDto.getContent());
    }

    @Transactional
    public void remove(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        findPost.remove();
    }
}
