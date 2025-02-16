package com.Post.mapper;

import com.Post.domain.Post;
import com.Post.dto.CommentDto;
import com.Post.dto.post.CreatePostDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;
import com.Post.dto.post.UpdatePostDto;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class PostMapper {
    public static GetPostDto toGetPostDto(Post post) {

        if(post == null) return null;

        GetPostDto dto = new GetPostDto();
        dto.setId(post.getId());

        dto.setTitle(post.getTitle());

        dto.setContent(post.getContent());

        dto.setUsername(post.getMember().getUsername());

        dto.setViewCount(post.getViewCount());

        dto.setComments(post.getComments().stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText(), comment.getMember().getUsername()))
                .collect(Collectors.toList()));

        dto.setCreatedAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        dto.setUpdatedAt(post.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return dto;

    }

    public static CreatePostDto toCreatePostDto(Post post) {
        if(post == null) return null;
        CreatePostDto dto = new CreatePostDto();


        dto.setTitle(post.getTitle());

        dto.setContent(post.getContent());


        dto.setUsername(post.getMember().getUsername());



        return dto;
    }

    public static UpdatePostDto toUpdatePostDto(Post post) {
        if(post == null) return null;

        UpdatePostDto dto = new UpdatePostDto();

        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());

        return dto;
    }
}
