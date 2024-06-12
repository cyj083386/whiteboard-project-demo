package com.example.whiteboard.mapper;

import com.example.whiteboard.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {
    Teacher findTeacherByUsername(String username);

    void insertTeacher(Teacher teacher);
}
