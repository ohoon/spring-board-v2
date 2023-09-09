package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {

    private Long postId;

    private Long memberId;

    private String title;

    private long totalComments;

    private String author;

    private Long view;

    private long totalVotes;

    private LocalDateTime createdDate;
}
