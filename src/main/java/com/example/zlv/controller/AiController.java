package com.example.zlv.controller;

import com.example.zlv.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.model.Media;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

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
    public Flux<String> chat(@RequestParam("prompt") String prompt, @RequestParam("chatId")String chatId,
                             @RequestParam(value = "files",required = false) List<MultipartFile> files) {

        // 1.保存会话
        chatHistoryRepository.save("chat", chatId);

        if (files == null || files.isEmpty()) {
            return textChat(prompt, chatId);

        } else {
            return multiModelChat(prompt, chatId,files);

        }
        // 2.请求模型

    }

    private Flux<String> textChat(String prompt, String chatId) {
        return chatClient.prompt()
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .user(prompt)
                .stream().content();
    }
    private Flux<String> multiModelChat(String prompt, String chatId,List<MultipartFile> files) {
        List<Media> medias = files.stream()
                .map(file -> new Media(MimeType.valueOf(file.getContentType()), file.getResource()))
                .toList();
        return chatClient.prompt()
                .user(p->p.text(prompt).media(medias.toArray(Media[]::new)))
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream().content();
    }


}
