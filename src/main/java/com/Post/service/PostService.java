package com.Post.service;

import com.Post.domain.Post;
import com.Post.domain.member.Member;
import com.Post.dto.post.CreatePostDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.dto.post.UpdatePostDto;
import com.Post.mapper.PostMapper;
import com.Post.repository.MemberRepository;
import com.Post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //모든 게시물을 조회하는 service
    public Page<MainPostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> new MainPostDto(
                        post.getId(),
                        post.getTitle(),
                        post.getViewCount(),
                        post.getMember().getUsername()
                ));
    }

    //memberId를 통해 post를 불러오는 service
    public List<MainPostDto> getPostsByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId)
                .stream()
                .map(post -> new MainPostDto(
                        post.getId(),
                        post.getTitle(),
                        post.getViewCount(),
                        post.getMember().getUsername()// memberId를 이용해 회원 정보 조회

                ))
                .collect(Collectors.toList());
    }

    //post의 id를 통해 post를 조회하는 service
    public GetPostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return PostMapper.toGetPostDto(post);
    }

    //post를 생성을 위한 service
    public CreatePostDto getCreatePostDto(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return PostMapper.toCreatePostDto(post);
    }


    //post생성 처리를 위한 service
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

    //post update를 위한 service
    public UpdatePostDto updatePost(Long postId, UpdatePostDto dto) {
        Post post = postRepository.findById(postId).orElse(null);


        if(dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력하세요!");
        }

        


        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        post.setUpdatedAt(LocalDateTime.now());


        postRepository.save(post);

        return PostMapper.toUpdatePostDto(post);
    }


    //post를 삭제하기 위한 service
    public void deletePost(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.");

        }
        postRepository.deleteById(postId);
    }

    //조회수 증가를 위한 service
    public void increaseViewCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

    }


}
