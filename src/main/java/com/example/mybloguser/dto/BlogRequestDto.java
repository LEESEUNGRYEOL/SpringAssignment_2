package com.example.mybloguser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BlogRequestDto {
    private String contents;
    private String title;
}
