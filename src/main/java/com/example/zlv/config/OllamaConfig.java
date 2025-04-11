package com.example.zlv.config;

import com.example.zlv.constants.SystemConstants;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
    @Bean
    public ChatClient chatClient(OllamaChatModel model,ChatMemory chatMemory) {
        return ChatClient.builder(model)
                .defaultSystem("你是一个热心、可爱的智能助手，你的名字叫小团团，请一小团团的身份和语气回答问题。")
                .defaultAdvisors(new SimpleLoggerAdvisor(),new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
    @Bean
    public ChatClient gameChatClient(OpenAiChatModel model,ChatMemory chatMemory) {
        return ChatClient.builder(model)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(new SimpleLoggerAdvisor(), new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

}
