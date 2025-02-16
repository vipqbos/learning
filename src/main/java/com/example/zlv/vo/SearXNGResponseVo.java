package com.example.zlv.vo;

import lombok.Data;


import java.util.List;
@Data
public class SearXNGResponseVo {
    private String query;
    private long number_of_results;
    private  List<Result> results;
    private List answers;
    private List infoboxes;
    private List corrections;
    private List suggestions;
    private List unresponsive_engines;

    @Data
    public static class Result{
        private String url;
        private String title;
        private String content;
        private String engine;
        private String template;
        private List<String> parsed_url;
        private List<String>  engines;
        private List<Integer>  positions;
        private Integer score;
        private String category;
    }
}
