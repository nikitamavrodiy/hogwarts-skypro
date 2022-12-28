package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student.getId() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id must be empty!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(studentService.getStudent(studentId));
    }

    @GetMapping(params = {"age"})
    public Collection<Student> findStudentsByAge(@RequestParam(required = false) int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public Collection<Student> findByAgeBetween(
            @RequestParam(required = false) int minAge,
            @RequestParam(required = false) int maxAge) {
        return this.studentService.findStudentsByAge(minAge, maxAge);
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/facultyOf/{studentId}")
    public Faculty getStudentFaculty(@PathVariable Long studentId) {
        return studentService.getStudent(studentId).getFaculty();
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(studentId, student));
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().body("Студент с айди " + studentId + " удален");
    }

}
