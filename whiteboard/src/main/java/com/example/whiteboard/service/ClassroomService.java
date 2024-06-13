package com.example.whiteboard.service;

import com.example.whiteboard.mapper.ClassroomMapper;
import com.example.whiteboard.model.Classroom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassroomService {

    private final ClassroomMapper classroomMapper;

    public void createClassroom(Classroom classroom) {
        classroomMapper.insertClassroom(classroom);
    }

    public Classroom getClassroomByTeacherId(int teacherId) {
//        log.info(classroomMapper.findClassroomByTeacherId(teacherId)
        return classroomMapper.findClassroomByTeacherId(teacherId);

    }

    public Classroom getClassroomByCode(String code) {
        return classroomMapper.findClassroomByCode(code);
    }
}
