package com.felipepossari.schoolregistration.unit.base.domain;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

import java.util.List;

public class CourseTestBuilder {

    private Long id = DefaultConstants.COURSE_ID;
    private String name = DefaultConstants.COURSE_NAME;
    private List<Student> students = null;

    private CourseTestBuilder() {
    }

    public static CourseTestBuilder aCourse() {
        return new CourseTestBuilder();
    }

    public CourseTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CourseTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CourseTestBuilder students(List<Student> students) {
        this.students = students;
        return this;
    }

    public Course build() {
        return Course.builder()
                .id(id)
                .students(students)
                .name(name)
                .build();
    }
}
