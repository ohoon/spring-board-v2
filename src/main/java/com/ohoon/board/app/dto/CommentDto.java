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
public class CommentDto extends CommentLeafDto {

    private final List<CommentLeafDto> children = new ArrayList<>();

    public CommentDto(Long commentId,
                       Long memberId,
                       Long postId,
                       String content,
                       String author,
                       boolean isRemoved,
                       LocalDateTime createdDate,
                       List<CommentLeafDto> children) {
        super(commentId, memberId, postId, content, author, isRemoved, createdDate);
        this.children.addAll(children);
    }
}
