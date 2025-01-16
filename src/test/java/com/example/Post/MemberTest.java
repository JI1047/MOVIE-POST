package com.example.Post;

import com.Post.domain.member.Member;
import com.Post.domain.member.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {

    @Test
    public void testMemberCreation() {
        // Arrange: Member 객체 생성 및 초기화
        Member member = new Member();
        member.setUsername("testUser");
        member.setPassword("testPassword");
        member.setEmail("test@example.com");
        member.setRole(Role.USER);

        // Act & Assert: 필드 값 검증
        assertEquals("testUser", member.getUsername(), "Username should be 'testUser'");
        assertEquals("testPassword", member.getPassword(), "Password should be 'testPassword'");
        assertEquals("test@example.com", member.getEmail(), "Email should be 'test@example.com'");
        assertEquals(Role.USER, member.getRole(), "Role should be 'USER'");


        System.out.println("Test Passed! Member created successfully:");
        System.out.println("Username: " + member.getUsername());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Role: " + member.getRole());
    }
}
