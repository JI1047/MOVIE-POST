package com.Post.dto.member;

import com.Post.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPageDto {

    private String email;

    private String username;

    private String context;

    private String createdAt;

    private Role role;


}
