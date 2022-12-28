package ru.hogwarts.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    private final String name;
    private final int age;

    public StudentDTO(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
