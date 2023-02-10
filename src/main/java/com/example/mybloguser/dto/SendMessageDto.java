package com.example.mybloguser.dto;

import lombok.Getter;

@Getter
public class SendMessageDto {
    private String message;
    public void sendMessage(String message)
    {
        this.message = message;
    }
}
