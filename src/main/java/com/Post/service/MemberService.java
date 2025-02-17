package com.Post.service;


import com.Post.domain.member.Member;
import com.Post.dto.member.EditDto;
import com.Post.dto.member.LoginRequestDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.dto.member.MyPageDto;
import com.Post.mapper.MemberMapper;
import com.Post.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입을 처리하는 service
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
        member.setContext(dto.getText());
        memberRepository.save(member);

    }

    //login을 처리하는 service
    public LoginRequestDto LoginMember(LoginRequestDto dto) {


        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 패스워드 입니다.");
        }

        return MemberMapper.toLoginRequestDto(member);
    }

    //회원 상세 정보를 처리하는 service
    public MyPageDto MyPageMember(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow();
        return MemberMapper.toMyPageDto(member);

    }

    //회원 수정시 정보를 제공하는 service
    public EditDto EditMemberInfo(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow();
        return MemberMapper.toEditDto(member);
    }


    //회원 수정시 정보를 처리하는 service
    public EditDto EditMember(Long userId, EditDto dto) {
        // 데이터베이스에서 회원 정보 조회
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

//        // 기존 비밀번호와 입력된 비밀번호 비교
//        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
//            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
//        }

        // 변경된 값이 없는지 확인
        if (isNoChanges(dto, member)) {
            throw new IllegalArgumentException("변경된 정보가 없습니다.");
        }

        // 변경된 값 업데이트
        if (dto.getUsername() != null && !dto.getUsername().equals(member.getUsername())) {
            member.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(member.getEmail())) {
            member.setEmail(dto.getEmail());
        }
        if (dto.getContext() != null && !dto.getContext().equals(member.getContext())) {
            member.setContext(dto.getContext());
        }

        // 비밀번호 변경 처리 (옵션)
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // 저장 후 반환
        memberRepository.save(member);

        return MemberMapper.toEditDto(member);
    }

    // 변경 사항이 없는지 확인하는 메서드
    private boolean isNoChanges(EditDto dto, Member member) {
        return (dto.getUsername() == null || dto.getUsername().equals(member.getUsername())) &&
                (dto.getEmail() == null || dto.getEmail().equals(member.getEmail())) &&
                (dto.getContext() == null || dto.getContext().equals(member.getContext())) &&
                (dto.getPassword() == null || dto.getPassword().isEmpty());
    }
    //회원 삭제를 처리하는 service
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }

        memberRepository.deleteById(id); // 회원 삭제
    }


}