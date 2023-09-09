package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.CommentDto;
import com.ohoon.board.app.dto.CommentLeafDto;
import com.ohoon.board.app.dto.CommentWriteDto;
import com.ohoon.board.app.exception.CommentNotFoundException;
import com.ohoon.board.app.exception.MemberNotFoundException;
import com.ohoon.board.app.exception.PostNotFoundException;
import com.ohoon.board.app.repository.CommentRepository;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.app.repository.PostRepository;
import com.ohoon.board.app.util.Mapper;
import com.ohoon.board.domain.Comment;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final Mapper mapper;

    @Transactional
    public Long write(Long memberId, Long postId, CommentWriteDto commentWriteDto) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        Comment savedComment = commentRepository.save(mapper.toComment(commentWriteDto, findMember, findPost));
        return savedComment.getId();
    }

    @Transactional
    public Long writeReply(Long memberId, Long postId, Long commentId, CommentWriteDto commentWriteDto) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        Comment child = mapper.toComment(commentWriteDto, findMember, findPost);
        child.assignParent(findComment);
        Comment savedComment = commentRepository.save(child);
        return savedComment.getId();
    }

    public Page<CommentDto> listByPostId(Long postId, Pageable pageable) {
        return commentRepository.listByPostId(postId, pageable)
                .map(mapper::toCommentDto);
    }

    public List<CommentLeafDto> recentList() {
        return commentRepository.findFirst6ByOrderByIdDesc().stream()
                .map(mapper::toCommentLeafDto)
                .toList();
    }

    @Transactional
    public void remove(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        findComment.remove();
    }

    public long count(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    public boolean isAuthor(Long commentId, Long memberId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        return findComment.getMemberId().equals(memberId);
    }
}
