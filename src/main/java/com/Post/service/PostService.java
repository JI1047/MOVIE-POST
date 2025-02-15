package com.Post.service;

import com.Post.domain.Post;
import com.Post.domain.member.Member;
import com.Post.dto.post.CreatePostDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.mapper.PostMapper;
import com.Post.repository.MemberRepository;
import com.Post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private PostRepository postRepository;

    private MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

    public void createPost(CreatePostDto dto) {
        if(dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력하세요!");
        }

        if (postRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다.");
        }


        Member findMember = memberRepository.findByUsername(dto.getUsername());

        System.out.println(findMember.getUsername());
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setMember(findMember);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setViewCount(0);

        postRepository.save(post);
    }

}
