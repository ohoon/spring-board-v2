package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Comment;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentWriteDto {

    private String content;

    public Comment toEntity(Member member, Post post) {
        return Comment.create(this.content, member, post);
    }
}
