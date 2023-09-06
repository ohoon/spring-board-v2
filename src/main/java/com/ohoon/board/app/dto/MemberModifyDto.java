package com.ohoon.board.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyDto {

    private String username;

    @Size(min = 2, max = 12,
            message = "닉네임은 2글자 ~ 12글자 이어야 합니다.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    public static MemberModifyDto fromProfileDto(MemberProfileDto profileDto) {
        return new MemberModifyDto(
                profileDto.getUsername(),
                profileDto.getNickname(),
                profileDto.getEmail(),
                profileDto.getCreatedDate(),
                profileDto.getLastModifiedDate()
        );
    }
}
