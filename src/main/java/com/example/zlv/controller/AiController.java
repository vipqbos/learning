package com.example.zlv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {
    private final ChatClient chatClient;

    @GetMapping("/chat")
    public String chat(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }


    @GetMapping(value = "/chatFlux",produces = "text/html;charset=utf-8")
    public Flux<String> chatFlux(String prompt) {
        return chatClient.prompt().user(prompt).stream().content();
    }
}
