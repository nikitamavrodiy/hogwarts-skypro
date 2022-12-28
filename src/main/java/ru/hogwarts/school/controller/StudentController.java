package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
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
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        if (studentDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id must be empty!");
        }
        return studentService.createStudent(studentDTO);
    }

    @GetMapping("/{studentId}")
    public StudentDTO getStudent(@PathVariable Long studentId) {
        return studentService.getStudent(studentId);
    }

    @GetMapping(params = {"age"})
    public Collection<StudentDTO> findStudentsByAge(@RequestParam(required = false) int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public Collection<StudentDTO> findByAgeBetween(
            @RequestParam(required = false) int minAge,
            @RequestParam(required = false) int maxAge) {
        return this.studentService.findStudentsByAge(minAge, maxAge);
    }

    @GetMapping("/all")
    public Collection<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/facultyByStudentId/{studentId}")
    public FacultyDTO getFacultyByStudentId(@PathVariable Long studentId) {
        return studentService.getFacultyByStudentId(studentId);
    }

    @PutMapping("/update/{studentId}")
    public StudentDTO updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(studentId, studentDTO);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }
}
