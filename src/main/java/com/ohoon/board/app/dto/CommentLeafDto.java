package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLeafDto {

    private Long commentId;

    private Long memberId;

    private Long postId;

    private String content;

    private String author;

    private boolean isRemoved;

    private LocalDateTime createdDate;
}
