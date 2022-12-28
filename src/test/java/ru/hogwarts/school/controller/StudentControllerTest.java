package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private StudentController studentController;

    private static final Student studentMock = new Student(1L, "Test", 99);
    private static final StudentDTO studentMockDTO = new StudentDTO(1L, "Test", 99);
    private static final Faculty facultyMock = new Faculty(10L, "TestFaculty", "red");

    @BeforeEach
    public void init() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentMock));
        when(studentRepository.findByAge(99)).thenReturn(List.of(studentMock));
        when(studentRepository.findByAgeBetween(90, 100)).thenReturn(List.of(studentMock));
        when(studentRepository.findAll()).thenReturn(List.of(studentMock));
        when(studentRepository.findByAgeBetween(10, 30)).thenReturn(Collections.emptyList());
        when(studentRepository.getReferenceById(1L)).thenReturn(studentMock);
        studentMock.setFaculty(facultyMock);
        when(studentRepository.save(StudentMapper.toEntity(studentMockDTO))).thenReturn(studentMock);
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() {
        ResponseEntity<Student> studentRs = this.restTemplate.getForEntity(buildUrl("student/1"), Student.class);

        Assertions.assertThat(studentRs.getBody()).isNotNull();
        Assertions.assertThat(studentRs.getBody().getId()).isEqualTo(studentMock.getId());
    }

    private String buildUrl(String otherPath) {
        return "http://localhost:" + port + "/" + otherPath;
    }

    @Test
    public void testFindStudentByAge() {
        List<Student> students = this.restTemplate.getForObject(buildUrl("student?age=99"), List.class);

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.contains(studentMock));
    }

    @Test
    public void testFindByAgeBetween() {
        List<Student> students = this.restTemplate.getForObject(buildUrl("student?minAge=90&maxAge=100"), List.class);
        List<Student> studentsEmptyList = this.restTemplate.getForObject(buildUrl("student?minAge=10&maxAge=30"), List.class);

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.contains(studentMock));
        Assertions.assertThat(studentsEmptyList).isEmpty();
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = this.restTemplate.getForObject(buildUrl("student/all"), List.class);

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.contains(studentMock));
        Assertions.assertThat(students.size()).isEqualTo(1);
    }

    @Test
    public void testGetFacultyByStudentId() {
        Faculty faculty = this.restTemplate.getForObject(buildUrl("student/facultyByStudentId/1"), Faculty.class);

        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getId()).isEqualTo(facultyMock.getId());
    }

    @Test
    public void testCreateStudent() {
        Student createdStudent = this.restTemplate.postForObject(buildUrl("student"), studentMock, Student.class);

        Assertions.assertThat(createdStudent.getId()).isEqualTo(studentMock.getId());
    }

    @Test
    public void testUpdateStudent() {
        Student studentNewName = new Student(1L, "TestUpdated", 50);
        this.restTemplate.put(buildUrl("student/update/1"), studentNewName, Student.class);

        Assertions.assertThat(studentMock.getAge()).isEqualTo(50);
    }

    @Test
    public void testDeleteStudent() {
        ResponseEntity<Void> resp = restTemplate.exchange(buildUrl("student/1"), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
