package com.example.whiteboard.controller;

import com.example.whiteboard.mapper.ClassroomMapper;
import com.example.whiteboard.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomMapper classroomMapper;

    @PostMapping
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        classroomMapper.insertClassroom(classroom);
        return classroom;
    }

    @GetMapping("/teacher/{teacherId}")
    public Classroom getClassroomByTeacherId(@PathVariable int teacherId) {
        return classroomMapper.findClassroomByTeacherId(teacherId);
    }

    @GetMapping("/{code}")
    public Classroom getClassroomByCode(@PathVariable String code) {
        return classroomMapper.findClassroomByCode(code);
    }
}
