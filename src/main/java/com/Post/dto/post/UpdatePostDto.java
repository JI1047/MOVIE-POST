package com.Post.dto.post;

import lombok.Getter;
import lombok.Setter;

//게시글의 업데이트를 위한 dto
@Getter
@Setter
public class UpdatePostDto {

    private String title;

    private String content;

}
