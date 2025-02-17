package com.Post.dto.member;

import com.Post.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

//회원정보를 불러오기 위한 dto
@Getter
@Setter
public class MyPageDto {

    private String email;

    private String username;

    private String context;

    private String createdAt;

    private Role role;


}
