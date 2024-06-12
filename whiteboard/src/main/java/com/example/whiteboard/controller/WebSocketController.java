package com.example.whiteboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/update")
    @SendTo("/topic/whiteboard")
    public String sendUpdate(String message) {
        logger.info("Received message: {}", message); // 로그 추가
        return message;
    }
}
