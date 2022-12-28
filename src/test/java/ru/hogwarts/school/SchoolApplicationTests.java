package ru.hogwarts.school;

import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.net.URI;
import java.security.cert.CertPathBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTests {

    @LocalServerPort
    private int port;

    private Long testFacultyId;

    private Faculty testFaculty;

    @Autowired StudentRepository studentRepository;

    @Autowired FacultyRepository facultyRepository;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void cleanUp() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        testFaculty = new Faculty();
        testFaculty.setName("TEST");
        testFaculty.setColor("TEST");
        testFacultyId = facultyRepository.save(testFaculty).getId();

    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudentTest() throws Exception {
        Student student = givenStudentWith("studentName", 25);
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        thenStudentHasBeenCreated(response);
    }

    @Test
    void getStudentByIdTest() throws Exception {
        Student student = givenStudentWith("studentName", 25);
        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(createResponse);
        Student createdStudent = createResponse.getBody();
        thenStudentWithIdHasBeenFound(createdStudent.getId(), createdStudent);
    }

    @Test
    void findByAgeTest() {
        Student student18 = givenStudentWith("studentName3", 18);
        Student student25 = givenStudentWith("studentName1", 25);
        Student student28 = givenStudentWith("studentName2", 28);
        Student student32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "25");
        thenStudentsAreFoundByCriteria(queryParams, student25);
    }

    @Test
    void findByAgeBetween() {
        Student student18 = givenStudentWith("studentName3", 18);
        Student student25 = givenStudentWith("studentName1", 25);
        Student student28 = givenStudentWith("studentName2", 28);
        Student student32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("minAge", "20");
        queryParams.add("maxAge", "30");
        thenStudentsAreFoundByCriteria(queryParams, student25, student28);
    }

    @Test
    void getAllStudents() {
        List<Student> students = this.restTemplate.getForObject("http://localhost:" + port + "/student/all", List.class);
        Assertions.assertThat(students).isEmpty();
        Student student18 = givenStudentWith("studentName3", 18);
        Student student25 = givenStudentWith("studentName1", 25);
        Student student28 = givenStudentWith("studentName2", 28);
        Student student32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student32);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all", List.class)
                .containsAll(List.of(student18, student32, student25, student28)));
    }

    @Test
    void deleteStudent() {
        Student student = givenStudentWith("badStudent", 18);
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        response.getBody().setFaculty(this.facultyRepository.getReferenceById(testFacultyId));
        thenStudentHasBeenCreated(response);
        Assertions.assertThat(this.studentRepository.findAll().contains(student));
        this.restTemplate.delete("http://localhost:"+port+"/student/1");
        Assertions.assertThat(this.studentRepository.findAll().contains(student)).isFalse();
    }

    @Test
    void updateStudent() {
        Student student = givenStudentWith("badStudent", 18);
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(this.studentRepository.findAll().contains(student));
        Student newStudent = givenStudentWith("newStudent", 19);
        this.restTemplate.put("http://localhost:" + port + "/student/update/"+response.getBody().getId(), newStudent);
        ResponseEntity<Student> actual = this.restTemplate.getForEntity("http://localhost:" + port + "/student/"+response.getBody().getId(), Student.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(actual.getBody()).isNotNull();
        Assertions.assertThat(actual.getBody().getAge()).isEqualTo(19);
        Assertions.assertThat(actual.getBody().getName()).isEqualTo("newStudent");
    }

    @Test
    public void getStudentFaculty() throws Exception {
        Student student = givenStudentWith("Student", 18);
        ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Student createdStudent = response.getBody();;
//        testFaculty.setStudents(Set.of(createdStudent));
        createdStudent.setFaculty(facultyRepository.getReferenceById(testFacultyId));
        Faculty responseFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/student/facultyOf/"+createdStudent.getId(), Faculty.class);
        Assertions.assertThat(responseFaculty).isNotNull();
        Assertions.assertThat(responseFaculty.getId()).isEqualTo(testFacultyId);


    }

    private void thenStudentsAreFoundByCriteria(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();

        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(students);
    }

    private void resetIds(Collection<Student> students) {
        students.forEach(it -> it.setId(null));
    }

    private void thenStudentWithIdHasBeenFound(Long studentId, Student student) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/student");
    }

    private ResponseEntity<Student> whenSendingCreateStudentRequest(URI uri, Student student) {
        return this.restTemplate.postForEntity(uri, student, Student.class);
    }

    private Student givenStudentWith(String name, int age) {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        student.setFaculty(this.facultyRepository.getReferenceById(testFacultyId));
        return student;
    }
}
