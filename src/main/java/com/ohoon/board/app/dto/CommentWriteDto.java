package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Comment;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentWriteDto {

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(max = 255)
    private String content;
}
