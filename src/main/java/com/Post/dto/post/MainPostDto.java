package com.Post.dto.post;


import com.Post.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
