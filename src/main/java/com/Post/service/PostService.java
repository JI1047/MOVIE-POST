package com.Post.service;

import com.Post.domain.Post;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.mapper.PostMapper;
import com.Post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public List<MainPostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new MainPostDto(post.getId(), post.getTitle(), post.getViewCount(), post.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    public GetPostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return PostMapper.toGetPostDto(post);
    }

}
