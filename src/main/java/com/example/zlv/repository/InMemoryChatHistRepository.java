package com.example.zlv.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryChatHistRepository implements ChatHistoryRepository {
    private final Map<String, List<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String type, String chatId) {
        if (!chatHistory.containsKey(type)) {
            chatHistory.put(type, new ArrayList<>());
        }
        List<String> chatIDs = chatHistory.computeIfAbsent(type,k->new ArrayList<>());
        if (chatIDs.contains(chatId)) {
            return;
        }
        chatIDs.add(chatId);

    }

    @Override
    public List<String> getChatIds(String type) {
        return chatHistory.getOrDefault(type, List.of());
    }
}
