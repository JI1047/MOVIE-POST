package com.Post.controller;

import com.Post.dto.member.MemberSignupDto;
import com.Post.dto.post.CreatePostDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(@Validated @RequestBody CreatePostDto dto) {

        try{
            postService.createPost(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("글이 작성됐습니다!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
