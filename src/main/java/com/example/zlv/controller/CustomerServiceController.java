package com.example.zlv.controller;

import com.example.zlv.entity.pojo.CourseReservation;
import com.example.zlv.repository.ChatHistoryRepository;
import com.example.zlv.service.CourseReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class CustomerServiceController {
    private final ChatClient serviceChatClient;
    private final ChatHistoryRepository chatHistoryRepository;
    @RequestMapping(value = "/service",produces = "text/html;charset=utf-8")
    public String  service(String prompt, String chatId) {

        chatHistoryRepository.save("service", chatId);
        return serviceChatClient.prompt()
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .user(prompt)
                .call().content();
    }

}
