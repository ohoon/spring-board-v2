package com.ohoon.board.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberChangePasswordDto {

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20,
            message = "비밀번호는 8글자 ~ 20글자 이어야 합니다.")
    private String newPassword;
}
