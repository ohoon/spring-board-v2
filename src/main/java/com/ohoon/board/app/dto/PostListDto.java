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

    private int totalComment;

    private String author;

    private Long view;

    private LocalDateTime createdDate;

    public static PostListDto fromEntity(Post post) {
        return new PostListDto(
                post.getId(),
                post.getMemberId(),
                post.getTitle(),
                post.getTotalComments(),
                post.getAuthor(),
                post.getView(),
                post.getCreatedDate());
    }
}
