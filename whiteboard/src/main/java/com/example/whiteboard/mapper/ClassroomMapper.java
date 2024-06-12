package com.example.whiteboard.mapper;

import com.example.whiteboard.model.Classroom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassroomMapper {
    Classroom findClassroomByCode(String code);

    void insertClassroom(Classroom classroom);

    Classroom findClassroomByTeacherId(int teacherId);
}
