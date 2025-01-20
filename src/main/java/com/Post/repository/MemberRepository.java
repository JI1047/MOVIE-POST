package com.Post.repository;

import com.Post.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email); // 이메일 중복 체크

    boolean existsByUsername(String username); // 이메일 중복 체크

}
