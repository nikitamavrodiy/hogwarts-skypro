package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FacultyServiceTest {
    FacultyService facultyService;
    List<Faculty> facultyList = List.of(
            new Faculty(1L, "Факультет1", "Цвет1"),
            new Faculty(2L, "Факультет2", "Цвет2"),
            new Faculty(3L, "Факультет3", "Цвет3"),
            new Faculty(4L, "Факультет4", "Цвет4"),
            new Faculty(5L, "Факультет5", "Цвет5")
    );

    @BeforeEach
    void setUp() {
        this.facultyService = new FacultyService();
        facultyList.forEach(facultyService::createFaculty);
    }

    @Test
    void createFacultyTest() {
        Faculty newStudent = new Faculty(6L, "Факультет6", "Цвет6");
        assertThat(
                facultyService.createFaculty(newStudent))
                .isEqualTo(newStudent)
                .isIn(facultyService.getAllFaculties());
    }

    @Test
    void getFacultyTest() {
        Faculty student3 = facultyList.get(2);
        assertThat(facultyService.getFaculty(3L))
                .isEqualTo(student3)
                .isIn(facultyService.getAllFaculties());
    }

    @Test
    void updateFacultyTest() {
        Faculty studentForUpdate = new Faculty(3L, "Факультет3Updated", "Цвет3Updated");
        assertThat(
                facultyService.updateFaculty(3L, studentForUpdate))
                .isEqualTo(studentForUpdate)
                .isIn(facultyService.getAllFaculties());
        assertThat(
                facultyService.getAllFaculties())
                .doesNotContain(facultyList.get(2));
        assertThat(
                facultyService.getFaculty(3L))
                .isNotEqualTo(facultyList.get(2));
    }

    @Test
    void deleteFacultyTest() {
        assertThat(
                facultyService.deleteFaculty(5L))
                .isEqualTo(facultyList.get(4))
                .isNotIn(facultyService.getAllFaculties());
    }

    @Test
    void getAllFaculties() {
        assertThat(
                facultyService.getAllFaculties())
                .hasSize(5)
                .containsAll(facultyList);
    }
}
