package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.ObjectNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.badRequest().body("Факультетов с заданными параметрами не найдено");
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyService.getFaculty(facultyId));
    }

    @GetMapping("/studentsOf/{facultyId}")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyService.getFaculty(facultyId).getStudents());
    }

    @GetMapping("/filterByColor/{color}")
    public ResponseEntity<Collection<Faculty>> findFacultiesByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }

    @GetMapping("/all")
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();

    }

    @GetMapping("/search/{searchString}")
    public Faculty findFacultyByNameOrColor(@PathVariable("searchString") String searchString) {
        return this.facultyService.findByNameOrColor(searchString);
    }

    @PutMapping("/update/{facultyId}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long facultyId, @RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(facultyId, faculty));
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long facultyId) {
        Faculty deletedFaculty = facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().body("Факультет с айди " + deletedFaculty.getId() + " удален");
    }
}
