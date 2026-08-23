package com.ecommerce.ai_assistant_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ai_assistant_service.dto.ChatRequest;
import com.ecommerce.ai_assistant_service.dto.ChatResponse;
import com.ecommerce.ai_assistant_service.service.AiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(("api/ai"))
public class AiController {
	
	private AiService aiService;
	
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }
	
	@PostMapping("/chat")
	public ChatResponse  chat(@Valid @RequestBody ChatRequest message) {
		String response= aiService.chat(message.getMessage());
		
		
		return new ChatResponse(response);
		
		
	}

}
