package com.Post.repository;

import com.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    boolean existsByTitle(String title);

    List<Post> findByMemberId(Long memberId);


}
