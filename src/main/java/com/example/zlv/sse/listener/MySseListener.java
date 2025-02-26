package com.example.zlv.sse.listener;

import io.github.lnyocly.ai4j.listener.SseListener;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class MySseListener extends SseListener {
    private SseEmitter sseEmitter;
    @Override
    protected void send() {
        try {
            sseEmitter.send(this.getCurrData(), new MediaType("text", "plain", StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
