package com.felipepossari.schoolregistration.unit.base.domain;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

import java.util.List;

public class StudentTestBuilder {

    private Long id = DefaultConstants.STUDENT_ID;
    private String name = DefaultConstants.STUDENT_NAME;
    private String email = DefaultConstants.STUDENT_EMAIL;
    private List<Course> courses = null;

    private StudentTestBuilder() {
    }

    public static StudentTestBuilder aStudent() {
        return new StudentTestBuilder();
    }

    public StudentTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudentTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentTestBuilder id(String name) {
        this.name = name;
        return this;
    }

    public StudentTestBuilder courses(List<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Student build() {
        return Student.builder()
                .id(id)
                .courses(courses)
                .email(email)
                .name(name)
                .build();
    }
}
