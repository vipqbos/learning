package com.example.zlv.controller;

import com.example.zlv.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {
    private final ChatClient chatClient;
    private final ChatHistoryRepository chatHistoryRepository;

    @GetMapping("/chat")
    public String chat(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }


    @GetMapping(value = "/chatFlux",produces = "text/html;charset=utf-8")
    public Flux<String> chatFlux(String prompt) {
        return chatClient.prompt().user(prompt).stream().content();
    }

    @PostMapping(value = "/chat",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt,String chatId) {
        // 1.保存会话
        chatHistoryRepository.save("chat", chatId);
        // 2.请求模型
        return chatClient.prompt()
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .user(prompt)
                .stream().content();
    }
}
