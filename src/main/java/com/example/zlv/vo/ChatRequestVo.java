package com.example.zlv.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequestVo {
    private String model;
    private List<Message> messages;
}
