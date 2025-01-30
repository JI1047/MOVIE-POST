package com.Post.controller;

import com.Post.dto.member.EditDto;
import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.dto.member.MyPageDto;
import com.Post.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginRequestDto> loginMember(@Validated @RequestBody LoginRequestDto dto, HttpSession session) {
        LoginRequestDto response = memberService.LoginMember(dto);
        session.setAttribute("email", dto.getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MyPageDto> getUserInfo(@PathVariable Long userId) {
        // 예: 서비스에서 사용자 정보를 조회
        MyPageDto userInfo = memberService.MyPageMember(userId);

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 사용자 정보 없을 경우
        }
        return ResponseEntity.ok(userInfo); // 사용자 정보 반환
    }

    @GetMapping("/{userId}/edit")
    public ResponseEntity<EditDto> editUserInfo(@PathVariable Long userId) {
        EditDto userInfo = memberService.EditMemberInfo(userId);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/{userId}/edit")
    public ResponseEntity<EditDto> editUserInfo(@PathVariable Long userId, @RequestBody EditDto editData) {

        EditDto userInfo = memberService.EditMember(userId,editData);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userInfo);
    }
}