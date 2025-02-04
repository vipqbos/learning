package com.example.zlv.vo;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private String role;
    // 文本内容
    private String content;
    // 图片base64的图片
    private List<String> images;
}
