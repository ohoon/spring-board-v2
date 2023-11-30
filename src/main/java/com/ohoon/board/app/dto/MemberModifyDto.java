package com.ohoon.board.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberModifyDto {

    private String username;

    @Size(min = 2, max = 12,
            message = "닉네임은 2글자 ~ 12글자 이어야 합니다.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private List<String> roles = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    public MemberModifyDto(String username, String nickname, String email, List<String> roles, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.roles.addAll(roles);
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
