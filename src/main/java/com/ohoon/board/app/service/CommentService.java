package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.CommentEditDto;
import com.ohoon.board.app.dto.CommentListDto;
import com.ohoon.board.app.dto.CommentWriteDto;
import com.ohoon.board.app.exception.CommentNotFoundException;
import com.ohoon.board.app.exception.MemberNotFoundException;
import com.ohoon.board.app.exception.PostNotFoundException;
import com.ohoon.board.app.repository.CommentRepository;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.app.repository.PostRepository;
import com.ohoon.board.domain.Comment;
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
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    @Transactional
    public Long write(Long memberId, Long postId, CommentWriteDto commentWriteDto) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        Comment savedComment = commentRepository.save(commentWriteDto.toEntity(findMember, findPost));
        return savedComment.getId();
    }

    public Page<CommentListDto> list(Pageable pageable) {
        return commentRepository.list(pageable)
                .map(CommentListDto::fromEntity);
    }

    @Transactional
    public void edit(Long commentId, CommentEditDto commentEditDto) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        findComment.edit(commentEditDto.getContent());
    }

    @Transactional
    public void remove(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        findComment.remove();
    }
}
