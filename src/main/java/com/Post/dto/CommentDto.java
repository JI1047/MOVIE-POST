package com.Post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//댓글 dto
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private String username;
}