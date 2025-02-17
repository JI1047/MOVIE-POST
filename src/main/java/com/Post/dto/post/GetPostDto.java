package com.Post.dto.post;

import com.Post.domain.Comment;
import com.Post.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


//게시글을 불러오기위한 dto
@Getter
@Setter

public class GetPostDto {

    private Long id;

    private String title;

    private String content;

    private String username;

    private int viewCount;

    private List<CommentDto> comments;

    private String createdAt; // 생성 일자

    private String updatedAt; // 수정 일자

}
