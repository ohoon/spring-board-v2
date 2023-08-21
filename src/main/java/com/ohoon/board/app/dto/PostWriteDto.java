package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteDto {

    private String title;

    private String content;

    public Post toEntity(Member member) {
        return Post.create(this.title, this.content, member);
    }
}
