package com.Post.service;


import com.Post.domain.member.Member;
import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerMember(MemberSignupDto dto) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // Member 엔티티 생성 및 저장
        Member member = new Member();
        member.setUsername(dto.getUsername());
        member.setEmail(dto.getEmail());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        // MemberResponseDto 반환
        memberRepository.save(member);
    }

    public Map<String,String> LoginMember(LoginRequestDto dto) {


        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 패스워드 입니다.");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "로그인 성공!");

        return response;
    }
}