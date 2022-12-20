package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return this.facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long facultyId) {
        return this.facultyRepository.findById(facultyId)
                .orElseThrow(FacultyNotFoundException::new);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        Faculty dbFaculty = this.facultyRepository.findById(facultyId)
                .orElseThrow(FacultyNotFoundException::new);
        dbFaculty.setName(faculty.getName());
        dbFaculty.setColor(faculty.getColor());
        return this.facultyRepository.save(dbFaculty);
    }

    public Faculty deleteFaculty(Long facultyId) {
        Faculty dbFaculty = this.facultyRepository.findById(facultyId)
                .orElseThrow(FacultyNotFoundException::new);
        this.facultyRepository.delete(dbFaculty);
        return dbFaculty;
    }

    public Collection<Faculty> getAllFaculties() {
        return this.facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return this.facultyRepository.findByColor(color);
    }

}
