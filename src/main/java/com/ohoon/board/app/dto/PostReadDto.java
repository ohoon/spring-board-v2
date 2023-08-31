package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReadDto {

    private Long postId;

    private Long memberId;

    private String title;

    private String content;

    private List<CommentListDto> commentListDtos;

    private String author;

    private Long view;

    private LocalDateTime createdDate;

    public static PostReadDto fromEntity(Post post) {
        return new PostReadDto(
                post.getId(),
                post.getMemberId(),
                post.getTitle(),
                post.getContent(),
                post.getComments().stream()
                        .map(CommentListDto::fromEntity)
                        .toList(),
                post.getAuthor(),
                post.getView(),
                post.getCreatedDate());
    }
}
