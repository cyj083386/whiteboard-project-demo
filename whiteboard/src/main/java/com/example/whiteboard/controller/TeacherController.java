package com.example.whiteboard.controller;

import com.example.whiteboard.mapper.TeacherMapper;
import com.example.whiteboard.model.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Teacher login(@RequestBody Teacher teacher) {
        Teacher existingTeacher = teacherMapper.findTeacherByUsername(teacher.getUsername());
        log.info(teacher.getPassword());
        log.info(existingTeacher.getPassword());
        if (existingTeacher != null && passwordEncoder.matches(teacher.getPassword(), existingTeacher.getPassword())) {
            return existingTeacher;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public Teacher register(@RequestBody Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherMapper.insertTeacher(teacher);
        return teacher;
    }
}
