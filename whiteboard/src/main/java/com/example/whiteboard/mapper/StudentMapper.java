package com.example.whiteboard.mapper;

import com.example.whiteboard.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    void insertStudent(Student student);
}
