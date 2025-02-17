package com.Post.dto.post;


import com.Post.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



//메인 페이지에 게시할 게시물을 불러오기 위한 dto
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MainPostDto {

    private Long id;

    private String title;

    private int viewCount;

    private String username;


}
