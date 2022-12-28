package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    private final Long id = 1L;
    private final String name = "Test";
    private final String color = "Yellow";
    private Faculty faculty;
    private JSONObject jsonObject;

    @BeforeEach
    public void setUp() throws Exception {
        faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("color", color);

    }

    @Test
    public void testGetFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        when(facultyRepository.getReferenceById(id)).thenReturn(faculty);

        Long studentId = 1L;
        String studentName = "HarryTester";
        int studentAge = 18;

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);

        Set<Student> students = Set.of(student);
        faculty.setStudents(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/studentsOf/{facultyId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }

    @Test
    public void testFindFacultiesByColor() throws Exception {
        when(facultyRepository.findByColor(color)).thenReturn(Arrays.asList(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filterByColor/{color}", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(Arrays.asList(jsonObject).toString()));
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(Arrays.asList(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(Arrays.asList(jsonObject).toString()));
    }

    @Test
    public void testFindFacultyByNameOrColor() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(color, color)).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, name)).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/search/{searchString}", name, color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));
    }

    @Test
    public void testCreateFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/update/{facultyId}", id, jsonObject)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{facultyId}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void whenObjectNotFoundThenReturnBadRequestWithBody() throws Exception {
        when(facultyRepository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Факультетов с заданными параметрами не найдено"));
    }
}
