package com.felipepossari.schoolregistration.application.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Course {
    private Long id;
    private String name;
    private List<Student> students;
}
