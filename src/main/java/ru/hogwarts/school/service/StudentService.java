package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exceptions.ObjectNotFoundException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        return StudentMapper.toDto(this.studentRepository.save(StudentMapper.toEntity(studentDTO)));
    }

    public StudentDTO getStudent(Long studentId) {
        return StudentMapper.toDto(this.studentRepository.findById(studentId)
                .orElseThrow(ObjectNotFoundException::new));

    }

    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student dbStudent =
                this.studentRepository.findById(studentId).orElseThrow(ObjectNotFoundException::new);
        dbStudent.setName(studentDTO.getName());
        dbStudent.setAge(studentDTO.getAge());
        return StudentMapper.toDto(this.studentRepository.save(StudentMapper.toEntity(studentDTO)));
    }

    public void deleteStudent(Long studentId) {
        Student dbStudent =
                this.studentRepository.findById(studentId).orElseThrow(ObjectNotFoundException::new);
        this.studentRepository.delete(dbStudent);
    }

    public Collection<StudentDTO> getAllStudents() {
        return this.studentRepository.findAll()
                .stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByAge(int age){
        return this.studentRepository.findByAge(age)
                .stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }

    public Collection<StudentDTO> findStudentsByAge(int minAge, int maxAge) {
        return this.studentRepository.findByAgeBetween(minAge, maxAge)
                .stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }

    public FacultyDTO getFacultyByStudentId(Long studentId) {
        return FacultyMapper.toDto(this.studentRepository.getReferenceById(studentId).getFaculty());
    }

    public Integer getAllStudentsAmount() {
        return this.studentRepository.getStudentsAmount();
    }

    public Integer getStudentsAgeAverage() {
        return this.studentRepository.getStudentsAgeAverage();
    }

    public Collection<StudentDTO> getLastFiveStudents() {
        return this.studentRepository.getLastFiveStudents()
                .stream().map(StudentMapper::toDto).collect(Collectors.toList());
    }
}
