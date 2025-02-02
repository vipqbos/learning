package com.example.zlv.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponseVo {
    private String model;
    private String created_at;
    private Message message;
    private boolean done;

    private String done_reason;
    private long total_duration;
    private int prompt_eval_count;
    private long prompt_eval_duration;
    private int eval_count;
    private long eval_duration;
}
