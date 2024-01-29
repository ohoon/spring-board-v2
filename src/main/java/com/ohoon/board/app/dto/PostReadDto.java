package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReadDto {

    private boolean isNotice;

    private Long postId;

    private Long memberId;

    private String title;

    private String content;

    private String author;

    private long view;

    private long totalVotes;

    private LocalDateTime createdDate;
}
