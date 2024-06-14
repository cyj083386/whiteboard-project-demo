package com.example.whiteboard.controller;

import com.example.whiteboard.mapper.TeacherMapper;
import com.example.whiteboard.model.Teacher;
import com.example.whiteboard.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/login")
    public String login(@RequestBody Teacher teacher) {
        return teacherService.login(teacher);
    }

    @PostMapping("/register")
    public Teacher register(@RequestBody Teacher teacher) {
        return teacherService.register(teacher);
    }
}
