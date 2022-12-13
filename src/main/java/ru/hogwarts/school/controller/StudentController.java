package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ExceptionHandler(StudentAlreadyExistException.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.badRequest().body("Студент с таким id уже создан");
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/filter/{age}")
    public ResponseEntity<Collection<Student>> findStudents(@PathVariable int age) {
        Collection<Student> students = studentService.getAllStudents()
                .stream().filter(student -> student.getAge() == age)
                .toList();
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> allStudents = studentService.getAllStudents();
        if (allStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allStudents);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long studentId) {
        Student deletedStudent = studentService.deleteStudent(studentId);
        return ResponseEntity.ok(deletedStudent);
    }
}
