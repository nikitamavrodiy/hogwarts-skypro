package ru.hogwarts.school.mapper;

import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class StudentMapper {
    public static StudentDTO toDto(Student student) {
        return new StudentDTO(student.getId(), student.getName(), student.getAge());
    }

    public static Student toEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setAge(studentDTO.getAge());
        student.setName(studentDTO.getName());
        return student;
    }
}
