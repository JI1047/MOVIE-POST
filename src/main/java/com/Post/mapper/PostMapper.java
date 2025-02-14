package com.Post.mapper;

import com.Post.domain.Post;
import com.Post.dto.CommentDto;
import com.Post.dto.post.GetPostDto;
import com.Post.dto.post.MainPostDto;

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
}
