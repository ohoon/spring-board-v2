package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentDto extends CommentChildDto {

    private final List<CommentChildDto> children = new ArrayList<>();

    private CommentDto(Long commentId,
                       Long memberId,
                       Long postId,
                       String content,
                       String author,
                       boolean isRemoved,
                       LocalDateTime createdDate,
                       List<CommentChildDto> children) {
        super(commentId, memberId, postId, content, author, isRemoved, createdDate);
        this.children.addAll(children);
    }

    public static CommentDto fromEntity(Comment comment) {
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
                        .map(CommentChildDto::fromEntity)
                        .toList());
    }
}
