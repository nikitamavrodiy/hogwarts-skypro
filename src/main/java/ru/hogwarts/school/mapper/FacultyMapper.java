package ru.hogwarts.school.mapper;

import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Faculty;

public class FacultyMapper {
    public static FacultyDTO toDto(Faculty faculty) {
        return new FacultyDTO(faculty.getId(), faculty.getName(), faculty.getColor());
    }

    public static Faculty toEntity(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        faculty.setColor(facultyDTO.getColor());
        faculty.setName(facultyDTO.getName());
        return faculty;
    }
}
