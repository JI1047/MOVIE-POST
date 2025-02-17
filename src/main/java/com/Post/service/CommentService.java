package com.Post.service;

import com.Post.domain.Comment;
import com.Post.domain.Post;
import com.Post.domain.member.Member;
import com.Post.dto.Comment.CreateCommentDto;
import com.Post.repository.CommentRepository;
import com.Post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;

    }


    public void createComment(CreateCommentDto dto,Long postId) {
        Comment comment = new Comment();

        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        comment.setText(dto.getText());
        comment.setPost(findPost);
        comment.setMember(findPost.getMember());

        commentRepository.save(comment);
    }
}
