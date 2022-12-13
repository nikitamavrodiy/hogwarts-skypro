package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class StudentServiceTest {
    StudentService studentService;
    List<Student> studentList = List.of(
            new Student(1L, "Студент1", 18),
            new Student(2L,"Студент2", 18),
            new Student(3L,"Студент3", 18),
            new Student(4L,"Студент4", 18),
            new Student(5L,"Студент5", 18)
    );

    @BeforeEach
    void setUp() {
        this.studentService = new StudentService();
        studentList.forEach(studentService::createStudent);
    }

    @Test
    void createStudentTest() {
        Student newStudent = new Student(6L,"Студент6", 18);
        assertThat(
                studentService.createStudent(newStudent))
                .isEqualTo(newStudent)
                .isIn(studentService.getAllStudents());
    }

    @Test
    void getStudentTest() {
        Student student3 = studentList.get(2);
        assertThat(studentService.getStudent(3L))
                .isEqualTo(student3)
                .isIn(studentService.getAllStudents());
    }

    @Test
    void updateStudentTest() {
        Student studentForUpdate = new Student(3L, "Студент3Updated", 22);
        assertThat(
                studentService.updateStudent(3L, studentForUpdate))
                .isEqualTo(studentForUpdate)
                .isIn(studentService.getAllStudents());
        assertThat(
                studentService.getAllStudents())
                .doesNotContain(studentList.get(2));
        assertThat(
                studentService.getStudent(3L))
                .isNotEqualTo(studentList.get(2));
    }

    @Test
    void deleteStudentTest() {
        assertThat(
                studentService.deleteStudent(5L))
                .isEqualTo(studentList.get(4))
                .isNotIn(studentService.getAllStudents());
    }

    @Test
    void getAllStudents() {
        assertThat(
                studentService.getAllStudents())
                .hasSize(5)
                .containsAll(studentList);
    }
}
