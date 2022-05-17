package com.felipepossari.schoolregistration.application.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Student {
    private Long id;
    private String name;
    private String email;
    private List<Course> courses;

    public boolean isEnrolledInAnyCourse(){
        return courses != null && !courses.isEmpty();
    }
}
