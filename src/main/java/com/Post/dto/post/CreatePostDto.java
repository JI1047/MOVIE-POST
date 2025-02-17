package com.Post.dto.post;

import lombok.Getter;
import lombok.Setter;

//게시물 생성을 위한 dto
@Getter
@Setter
public class CreatePostDto {

    private String title;

    private String content;

    private String username;


}
