package com.example.mybloguser.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private Boolean success;
    private int statusCode;
    public MessageResponseDto(Boolean success, int statusCode)
    {
        this.success = success;
        this.statusCode = statusCode;
    }
}
