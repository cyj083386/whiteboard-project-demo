package com.example.whiteboard.controller;

import com.example.whiteboard.mapper.ClassroomMapper;
import com.example.whiteboard.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/update")
    @SendTo("/topic/whiteboard")
    public String sendUpdate(String message) {
        return message;
    }

    @MessageMapping("/join")
    @SendTo("/topic/join")
    public Map<String, String> joinClassroom(SimpMessageHeaderAccessor headerAccessor, Map<String, String> message) {
        return webSocketService.joinClassroom(headerAccessor, message);
    }

    @MessageMapping("/leave")
    @SendTo("/topic/leave")
    public Map<String, String> leaveClassroom(SimpMessageHeaderAccessor headerAccessor, Map<String, String> message) {
        return webSocketService.webSocketService(headerAccessor, message);
    }

    @GetMapping("/api/generateClassroomCode")
    public Map<String, String> generateClassroomCode() {
        return webSocketService.generateClassroomCode();
    }
}
