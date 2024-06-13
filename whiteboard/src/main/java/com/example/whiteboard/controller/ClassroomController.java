package com.example.whiteboard.controller;

import com.example.whiteboard.mapper.ClassroomMapper;
import com.example.whiteboard.model.Classroom;
import com.example.whiteboard.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping("/teacher/{teacherId}")
    public Classroom getClassroomByTeacherId(@PathVariable int teacherId) {
        return classroomService.getClassroomByTeacherId(teacherId);
    }

    @GetMapping("/{code}")
    public Classroom getClassroomByCode(@PathVariable String code) {
        return classroomService.getClassroomByCode(code);
    }
}
