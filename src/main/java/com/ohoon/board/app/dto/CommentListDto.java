package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Comment;
import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDto {

    private Long commentId;

    private Long memberId;

    private Long postId;

    private String content;

    private String author;

    private boolean isRemoved;

    public static CommentListDto fromEntity(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getMemberId(),
                comment.getPostId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.isRemoved());
    }
}
