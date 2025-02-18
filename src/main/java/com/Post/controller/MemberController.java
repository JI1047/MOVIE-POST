package com.Post.controller;

import com.Post.dto.member.EditDto;
import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.dto.member.MyPageDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.service.MemberService;
import com.Post.service.PostService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(title = "게시판 API", version = "1.0", description = "게시판 회원 관련 API 문서")
)

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    public MemberController(MemberService memberService, PostService postService) {
        this.memberService = memberService;
        this.postService = postService;
    }


    //회원가입시 POST api
    @PostMapping("/signup")
    public ResponseEntity<String> registerMember(@Validated @RequestBody MemberSignupDto dto) {
        try {
            memberService.registerMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //로그인시 Post api
    @PostMapping("/login")
    public ResponseEntity<LoginRequestDto> loginMember(@Validated @RequestBody LoginRequestDto dto, HttpSession session) {
        LoginRequestDto response = memberService.LoginMember(dto);
        session.setAttribute("email", dto.getEmail());

        return ResponseEntity.ok(response);
    }

    //회원 상세페이지 Get api
    @GetMapping("/{userId}")
    public ResponseEntity<MyPageDto> getUserInfo(@PathVariable Long userId) {
        MyPageDto userInfo = memberService.MyPageMember(userId);

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 사용자 정보 없을 경우
        }
        return ResponseEntity.ok(userInfo); // 사용자 정보 반환
    }

    //회원 수정 get api
    @GetMapping("/{userId}/edit")
    public ResponseEntity<EditDto> editUserInfo(@PathVariable Long userId) {
        EditDto userInfo = memberService.EditMemberInfo(userId);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userInfo);
    }

    //회원 수정 Put api
    @PutMapping("/{userId}/edit")
    public ResponseEntity<EditDto> editUserInfo(@PathVariable Long userId, @RequestBody EditDto editData) {

        EditDto userInfo = memberService.EditMember(userId, editData);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userInfo);
    }

    //회원 삭제 delete api
    @DeleteMapping("{userId}/delete")
    public ResponseEntity<String> deleteUserInfo(@PathVariable Long userId) {
        memberService.deleteMember(userId);
        return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
    }

    //회원이 작성한 게시글 조회 get api
    @GetMapping("{userId}/posts")
    public List<MainPostDto> getPosts(@PathVariable Long userId) {

        return postService.getPostsByMemberId(userId);
    }

}