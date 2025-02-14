package com.Post.controller;

import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<MainPostDto> getAllPost() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostDto> getPostById(@PathVariable Long postId) {
        GetPostDto post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(post);
    }
}
