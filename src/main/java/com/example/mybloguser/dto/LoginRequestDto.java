package com.example.mybloguser.dto;

import lombok.Getter;
import lombok.Setter;
// setter 추가 왜 된지 알아볼것.
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}