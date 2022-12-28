package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exceptions.ObjectNotFoundException;
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
    public FacultyDTO createFaculty(@RequestBody FacultyDTO faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{facultyId}")
    public FacultyDTO getFaculty(@PathVariable Long facultyId) {
        return facultyService.getFaculty(facultyId);
    }

    @GetMapping("/studentsOf/{facultyId}")
    public Collection<StudentDTO> getStudentsByFaculty(@PathVariable Long facultyId) {
        return facultyService.getStudentsByFacultyId(facultyId);
    }

    @GetMapping("/filterByColor/{color}")
    public Collection<FacultyDTO> findFacultiesByColor(@PathVariable String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/all")
    public Collection<FacultyDTO> getAllFaculties() {
        return facultyService.getAllFaculties();

    }

    @GetMapping("/search/{searchString}")
    public FacultyDTO findFacultyByNameOrColor(@PathVariable("searchString") String searchString) {
        return this.facultyService.findByNameOrColor(searchString);
    }

    @PutMapping("/update/{facultyId}")
    public FacultyDTO updateFaculty(@PathVariable Long facultyId, @RequestBody FacultyDTO facultyDTO) {
        return facultyService.updateFaculty(facultyId, facultyDTO);
    }

    @DeleteMapping("/{facultyId}")
    public FacultyDTO deleteFaculty(@PathVariable("facultyId") Long facultyId) {
        return facultyService.deleteFaculty(facultyId);
    }
}
