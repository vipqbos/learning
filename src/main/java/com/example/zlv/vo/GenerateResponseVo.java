package com.example.zlv.vo;

import lombok.Data;

@Data
public class GenerateResponseVo {
    private String model;
    private String created_at;
    private String response;
    private boolean done;

    private String done_reason;
    private int[] context;

    private long total_duration;
    private int prompt_eval_count;
    private long prompt_eval_duration;
    private int eval_count;
    private long eval_duration;
}
