package com.Post.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 회원수정을 위한 dto
@Getter
@Setter
public class EditDto {
    @Size(min = 2, max = 8, message = "닉네임은 2~8자 이내여야 합니다.")
    private String username;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    private String context;
}
