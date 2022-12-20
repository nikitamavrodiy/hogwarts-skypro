package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId) {
        return this.studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

    }

    public Student updateStudent(Long studentId, Student student) {
        Student dbStudent =
                this.studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        dbStudent.setName(student.getName());
        dbStudent.setAge(student.getAge());
        return this.studentRepository.save(dbStudent);
    }

    public void deleteStudent(Long studentId) {
        this.studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudents() {
        return this.studentRepository.findAll();

    }

    public Collection<Student> getStudentsByAge(int age){
        return this.studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentsByAge(int minAge, int maxAge) {
        return this.studentRepository.findByAgeBetween(minAge, maxAge);
    }
}
