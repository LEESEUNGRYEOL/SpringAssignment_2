package com.example.mybloguser.dto;

import lombok.Getter;

@Getter
public class SendMessageDto {
    private String message;
    private int statuscode
    public void sendMessage(String message, int statuscode)
    {
        this.message = message;
        this.statuscode = statuscode;
    }
}
