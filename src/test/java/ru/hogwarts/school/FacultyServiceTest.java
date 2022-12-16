package ru.hogwarts.school;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.exceptions.FacultyNotFoundException;
//import ru.hogwarts.school.exceptions.StudentNotFoundException;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.service.FacultyService;
//import ru.hogwarts.school.service.StudentService;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
public class FacultyServiceTest {
////    FacultyService facultyService;
//    List<Faculty> facultyList = List.of(
////            new Faculty(0L, "Факультет1", "Цвет1"),
////            new Faculty(0L, "Факультет2", "Цвет2"),
////            new Faculty(0L, "Факультет3", "Цвет3"),
////            new Faculty(4L, "Факультет4", "Цвет4"),
////            new Faculty(5L, "Факультет5", "Цвет5")
//    );
//
//    @BeforeEach
//    void setUp() {
//        this.facultyService = new FacultyService();
//        facultyList.forEach(facultyService::createFaculty);
//    }
//
//    @Test
//    void createFacultyTest() {
////        Faculty newStudent = new Faculty(0L, "Факультет6", "Цвет6");
//        assertThat(
//                facultyService.createFaculty(newStudent))
//                .isEqualTo(newStudent)
//                .isIn(facultyService.getAllFaculties());
//    }
//
//    @Test
//    void getFacultyTest() {
//        Faculty student3 = facultyList.get(2);
//        assertThat(facultyService.getFaculty(3L))
//                .isEqualTo(student3)
//                .isIn(facultyService.getAllFaculties());
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.getFaculty(10L));
//    }
//
//    @Test
//    void updateFacultyTest() {
//        Faculty facultyNewUpdate = new Faculty(3L, "Факультет3Updated", "Цвет3Updated");
//        assertThat(
//                facultyService.updateFaculty(3L, facultyNewUpdate))
//                .isEqualTo(facultyNewUpdate)
//                .isIn(facultyService.getAllFaculties());
//        assertThat(
//                facultyService.getAllFaculties())
//                .doesNotContain(facultyList.get(2));
//        assertThat(
//                facultyService.getFaculty(3L))
//                .isNotEqualTo(facultyList.get(2));
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.updateFaculty(10L, facultyNewUpdate));
//    }
//
//    @Test
//    void deleteFacultyTest() {
//        assertThat(
//                facultyService.deleteFaculty(5L))
//                .isEqualTo(facultyList.get(4))
//                .isNotIn(facultyService.getAllFaculties());
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.deleteFaculty(10L));
//    }
//
//    @Test
//    void getAllFacultiesTest() {
//        assertThat(
//                facultyService.getAllFaculties())
//                .hasSize(5)
//                .containsAll(facultyList);
//        facultyService = new FacultyService();
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.getAllFaculties());
//    }
//
//    @Test
//    void getFacultiesByColorTest() {
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.getFacultiesByColor("Цвет9760"));
//        assertThat(
//                facultyService.getFacultiesByColor("Цвет1"))
//                .hasSize(1)
//                .contains(facultyList.get(0));
//        facultyService = new FacultyService();
//        Assertions.assertThrows(FacultyNotFoundException.class, () -> facultyService.getFacultiesByColor("Цвет1"));
//    }
}
