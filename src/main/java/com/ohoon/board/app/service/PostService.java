package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.exception.DuplicateVoteException;
import com.ohoon.board.app.exception.MemberNotFoundException;
import com.ohoon.board.app.exception.PostNotFoundException;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.app.repository.PostRepository;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import com.ohoon.board.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Long write(Long memberId, PostWriteDto postWriteDto) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        Post savedPost = postRepository.save(postWriteDto.toEntity(findMember));
        return savedPost.getId();
    }

    @Transactional
    public PostReadDto read(Long postId) {
        Post findPost = postRepository.findByIdForUpdate(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        findPost.increaseView();
        return PostReadDto.fromEntity(findPost);
    }

    public Page<PostListDto> list(PostSearchCondition condition, Pageable pageable) {
        return postRepository.list(condition, pageable)
                .map(PostListDto::fromEntity);
    }

    public List<PostListDto> recentList() {
        return postRepository.findFirst6ByOrderByIdDesc().stream()
                .map(PostListDto::fromEntity)
                .toList();
    }

    @Transactional
    public void edit(Long postId, PostEditDto postEditDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        findPost.edit(postEditDto.getTitle(), postEditDto.getContent());
    }

    @Transactional
    public void remove(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        findPost.remove();
    }

    public boolean isAuthor(Long postId, Long memberId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        return findPost.getMemberId().equals(memberId);
    }

    @Transactional
    public void vote(Long memberId, Long postId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        if (findPost.isVotedBy(memberId)) {
            throw new DuplicateVoteException("이미 추천한 게시글입니다.");
        }

        findPost.addVote(Vote.create(findMember, findPost));
    }
}
