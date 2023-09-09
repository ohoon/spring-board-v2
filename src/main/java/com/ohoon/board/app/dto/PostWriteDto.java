package com.ohoon.board.app.dto;

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
public class PostWriteDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(max = 64,
            message = "제목은 64글자 이하 이어야 합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
