package ru.hogwarts.school.service;

public class StudentServiceTest {
//    StudentService studentService;
//
//    Student createStudent(String name, int age) {
////        return new Student(0L, name, age);
//    }
//    List<Student> studentList = List.of(
//            createStudent("Студент1", 18),
//            createStudent("Студент2", 18),
//            createStudent("Студент3", 18),
//            createStudent("Студент4", 18),
//            createStudent("Студент5", 18)
//    );
//
//    @BeforeEach
//    void setUp() {
//        this.studentService = new StudentService();
//        studentList.forEach(studentService::createStudent);
//    }
//
//    @Test
//    void createStudentTest() {
//        Student newStudent = createStudent("Студент6", 18);
//        assertThat(
//                studentService.createStudent(newStudent))
//                .isEqualTo(newStudent)
//                .isIn(studentService.getAllStudents());
//    }
//
//    @Test
//    void getStudentTest() {
//        Student student3 = studentList.get(2);
//        assertThat(studentService.getStudent(3L))
//                .isEqualTo(student3)
//                .isIn(studentService.getAllStudents());
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(10L));
//    }
//
//    @Test
//    void updateStudentTest() {
//        Student studentForUpdate = createStudent("Студент3Updated", 22);
//        assertThat(
//                studentService.updateStudent(3L, studentForUpdate))
//                .isEqualTo(studentForUpdate)
//                .isIn(studentService.getAllStudents());
//        assertThat(
//                studentService.getAllStudents())
//                .doesNotContain(studentList.get(2));
//        assertThat(
//                studentService.getStudent(3L))
//                .isNotEqualTo(studentList.get(2));
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(10L, studentForUpdate));
//    }
//
//    @Test
//    void deleteStudentTest() {
//        assertThat(
//                studentService.deleteStudent(5L))
//                .isEqualTo(studentList.get(4))
//                .isNotIn(studentService.getAllStudents());
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(10L));
//    }
//
//    @Test
//    void getAllStudentsTest() {
//        assertThat(
//                studentService.getAllStudents())
//                .hasSize(5)
//                .containsAll(studentList);
//        studentService = new StudentService();
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getAllStudents());
//    }
//
//    @Test
//    void getStudentsByAgeTest() {
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentsByAge(19));
//        assertThat(
//                studentService.getStudentsByAge(18))
//                .hasSize(5)
//                .containsAll(studentList);
//        studentService = new StudentService();
//        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentsByAge(18));
//    }
}
