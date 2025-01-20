package com.Post.controller;

import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerMember(@Validated @RequestBody MemberSignupDto dto) {
        try {
            memberService.registerMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginMember(@Validated @RequestBody LoginRequestDto dto) {
        Map<String, String> response = memberService.LoginMember(dto);
        return ResponseEntity.ok(response);
    }

}