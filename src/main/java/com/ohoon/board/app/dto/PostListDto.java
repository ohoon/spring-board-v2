package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {

    private Long postId;

    private Long memberId;

    private String title;

    private String author;

    private Long view;

    public static PostListDto fromEntity(Post post) {
        return new PostListDto(
                post.getId(),
                post.getMemberId(),
                post.getTitle(),
                post.getAuthor(),
                post.getView());
    }
}
