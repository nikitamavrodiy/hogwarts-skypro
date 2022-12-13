package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistException;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long idCounter = 1L;

    public Faculty createFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            throw new FacultyAlreadyExistException();
        }
        facultyMap.put(idCounter, faculty);
        idCounter++;
        return faculty;
    }

    public Faculty getFaculty(Long facultyId) {
        return facultyMap.get(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        facultyMap.put(facultyId, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long facultyId) {
        return facultyMap.remove(facultyId);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyMap.values();
    }
}
