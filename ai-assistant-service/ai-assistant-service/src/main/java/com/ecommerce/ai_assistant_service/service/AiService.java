package com.ecommerce.ai_assistant_service.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class AiService {

	
	private final ChatClient chatClient;
	

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
	
	public String chat(String message) {
	return	chatClient.prompt().user(message).call().content();
	}
	
}
