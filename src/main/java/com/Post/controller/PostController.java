package com.Post.controller;

import com.Post.dto.Comment.CreateCommentDto;
import com.Post.dto.member.EditDto;
import com.Post.dto.member.MemberSignupDto;
import com.Post.dto.post.CreatePostDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.dto.post.UpdatePostDto;
import com.Post.service.CommentService;
import com.Post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService,CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    //mainPage에 표시되는 게시글 조회 get api
    @GetMapping
    public Page<MainPostDto> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return postService.getAllPosts(pageable);
    }

    //게시글 조회 get api
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostDto> getPostById(@PathVariable Long postId) {
        GetPostDto post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(post);
    }
    @PutMapping("/{postId}")
    public void increaseViewCount(@PathVariable Long postId) {
        postService.increaseViewCount(postId);
    }

    @PostMapping("{postId}/comments")
    public ResponseEntity<String> addComment(@PathVariable Long postId, @RequestBody CreateCommentDto dto) {
        try{
            commentService.createComment(dto,postId);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 작성됐습니다!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //게시글 생성 post api
    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(@Validated @RequestBody CreatePostDto dto) {

        try {
            postService.createPost(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("글이 작성됐습니다!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //게시글 수정시 기존의 제목과 내용을 불러오는 get api
    @GetMapping("/edit-post/{postId}")
    public ResponseEntity<CreatePostDto> editPost(@PathVariable Long postId) {

        CreatePostDto post = postService.getCreatePostDto(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(post);
    }

    //게시글 수정 put api
    @PutMapping("/edit-post/{postId}")
    public ResponseEntity<UpdatePostDto> editPost(@PathVariable Long postId, @Validated @RequestBody UpdatePostDto dto) {
        UpdatePostDto postInfo = postService.updatePost(postId, dto);
        if (postInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postInfo);
    }

    //게시글 삭제 delete api
    @DeleteMapping("edit-post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
