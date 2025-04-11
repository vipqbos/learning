package com.example.zlv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class GameController {
    private final ChatClient gameChatClient;

    @RequestMapping(value = "/game",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId) {
        return gameChatClient.prompt()
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .user(prompt)
                .stream().content();
    }
}
