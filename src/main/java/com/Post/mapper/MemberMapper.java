package com.Post.mapper;

import com.Post.domain.member.Member;
import com.Post.dto.member.EditDto;
import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MyPageDto;

import java.time.format.DateTimeFormatter;

public class MemberMapper {

    // Member -> MyPageDto 변환 메서드
    public static MyPageDto toMyPageDto(Member member) {
        if (member == null) {
            return null;
        }
        MyPageDto dto = new MyPageDto();
        dto.setUsername(member.getUsername());
        dto.setEmail(member.getEmail());
        dto.setContext(member.getContext());
        dto.setCreatedAt(member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setRole(member.getRole());
        return dto;
    }

    public static LoginRequestDto toLoginRequestDto(Member member) {
        if (member == null) {
            return null;


        }
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());
        dto.setId(member.getId());
        dto.setUsername(member.getUsername());
        return dto;
    }

    public static EditDto toEditDto(Member member) {
        if (member == null) {
            return null;
        }
        EditDto dto = new EditDto();
        dto.setUsername(member.getUsername());
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());
        dto.setContext(member.getContext());
        return dto;
    }
}
