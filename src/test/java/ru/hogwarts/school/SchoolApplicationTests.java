package ru.hogwarts.school;

import org.junit.jupiter.api.Test;

public class SchoolApplicationTests { ;

    @Test
    void contextLoads() {
    }

//    @BeforeEach
//    void cleanUp() {
//        studentRepository.deleteAll();
//        facultyRepository.deleteAll();
//        Faculty testFaculty = new Faculty();
//        testFaculty.setName("TEST");
//        testFaculty.setColor("TEST");
//        testFacultyId = facultyRepository.save(testFaculty).getId();
//
//
//    }

//    @Test
//    void createStudentTest() {
//        Student newStudent = new Student();
//        newStudent.setName(Faker.instance().harryPotter().character());
//        newStudent.setAge(Faker.instance().number().numberBetween(16, 24));
//        Faculty testFaculty = new Faculty();
//        testFaculty.setId(testFacultyId);
//        newStudent.setFaculty(testFaculty);
//
//        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", newStudent, Student.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody()).isNotNull();
//    }
//
//
//    @Test
//    void deleteStudent() {
//        Student newStudent = new Student();
//        newStudent.setName(Faker.instance().harryPotter().character());
//        newStudent.setAge(Faker.instance().number().numberBetween(16, 24));
//        newStudent.setFaculty(this.facultyRepository.getReferenceById(testFacultyId));
//        Long id = this.studentRepository.save(newStudent).getId();
//
//        assertThat(this.studentRepository.findAll().size()).isEqualTo(1);
//        restTemplate.delete("http://localhost:" + port + "/student/" + id);
//        assertThat(this.studentRepository.findAll()).isEmpty();
//    }

}
