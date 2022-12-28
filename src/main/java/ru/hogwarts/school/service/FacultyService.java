package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exceptions.ObjectNotFoundException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public FacultyDTO createFaculty(FacultyDTO faculty) {
        return FacultyMapper.toDto(this.facultyRepository.save(FacultyMapper.toEntity(faculty)));
    }

    public FacultyDTO getFaculty(Long facultyId) {
        return FacultyMapper.toDto(this.facultyRepository.findById(facultyId)
                .orElseThrow(ObjectNotFoundException::new));
    }

    public FacultyDTO updateFaculty(Long facultyId, FacultyDTO facultyDTO) {
        Faculty dbFaculty =
                this.facultyRepository.findById(facultyId).orElseThrow(ObjectNotFoundException::new);
        dbFaculty.setName(facultyDTO.getName());
        dbFaculty.setColor(facultyDTO.getColor());
        return FacultyMapper.toDto(this.facultyRepository.save(dbFaculty));
    }

    public FacultyDTO deleteFaculty(Long facultyId) {
        Faculty dbFaculty =
                this.facultyRepository.findById(facultyId).orElseThrow(ObjectNotFoundException::new);
        this.facultyRepository.delete(dbFaculty);
        return FacultyMapper.toDto(dbFaculty);
    }

    public Collection<FacultyDTO> getAllFaculties() {
        return this.facultyRepository.findAll().stream().map(FacultyMapper::toDto).collect(Collectors.toList());
    }

    public Collection<FacultyDTO> getFacultiesByColor(String color) {
        return this.facultyRepository.findByColor(color).stream().map(FacultyMapper::toDto).collect(Collectors.toList());
    }

    public FacultyDTO findByNameOrColor(String nameOrColor) {
        return this.facultyRepository
                .findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor)
                .map(FacultyMapper::toDto)
                .orElseThrow(ObjectNotFoundException::new);
    }

    public Collection<StudentDTO> getStudentsByFacultyId(Long facultyId) {
        return this.facultyRepository.getReferenceById(facultyId).getStudents()
                .stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }
}
