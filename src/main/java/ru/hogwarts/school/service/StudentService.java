package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private Map<Long, Student> studentMap = new HashMap<>();
    private Long idCounter = 1L;

    public Student createStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            throw new StudentAlreadyExistException();
        }
        studentMap.put(idCounter, student);
        idCounter++;
        return student;
    }

    public Student getStudent(Long studentId) {
        return studentMap.get(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        studentMap.put(studentId, student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        return studentMap.remove(studentId);
    }

    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }
}
