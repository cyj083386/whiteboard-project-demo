package com.example.whiteboard.controller;

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
public class WebSocketController {

    private Map<String, Map<String, String>> classroomStudents = new HashMap<>();

    @MessageMapping("/update")
    @SendTo("/topic/whiteboard")
    public String sendUpdate(String message) {
        return message;
    }

    @MessageMapping("/join")
    @SendTo("/topic/join")
    public Map<String, String> joinClassroom(SimpMessageHeaderAccessor headerAccessor, Map<String, String> message) {
        String classCode = message.get("classCode");
        String studentName = message.get("studentName");

        classroomStudents.putIfAbsent(classCode, new HashMap<>());
        classroomStudents.get(classCode).put(headerAccessor.getSessionId(), studentName);

        Map<String, String> joinMessage = new HashMap<>();
        joinMessage.put("studentName", studentName);
        joinMessage.put("sessionId", headerAccessor.getSessionId());
        joinMessage.put("classCode", classCode);

        return joinMessage;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/leave")
    public Map<String, String> leaveClassroom(SimpMessageHeaderAccessor headerAccessor, Map<String, String> message) {
        String classCode = message.get("classCode");

        String studentName = "";
        if (classroomStudents.containsKey(classCode)) {
            studentName = classroomStudents.get(classCode).remove(headerAccessor.getSessionId());
        }

        Map<String, String> leaveMessage = new HashMap<>();
        leaveMessage.put("sessionId", headerAccessor.getSessionId());
        leaveMessage.put("classCode", classCode);

        return leaveMessage;
    }

    @GetMapping("/api/generateClassroomCode")
    public Map<String, String> generateClassroomCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 100000에서 999999 사이의 숫자를 생성
        Map<String, String> response = new HashMap<>();
        response.put("classCode", String.valueOf(code));
        return response;
    }
}
