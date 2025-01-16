package com.example.zlv.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseEntity<T> {
    private String message;
    private T data;
    private int code;



    public static <T> ResponseEntity<T> ok(T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(data);
        responseEntity.setCode(200);
        responseEntity.setMessage("success");
        return responseEntity;
    }
}
