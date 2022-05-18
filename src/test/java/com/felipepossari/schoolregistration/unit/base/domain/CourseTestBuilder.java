package com.felipepossari.schoolregistration.unit.base.domain;

import antlr.collections.impl.IntRange;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CourseTestBuilder {

    private Long id = DefaultConstants.COURSE_ID;
    private String name = DefaultConstants.COURSE_NAME;
    private List<Student> students = new ArrayList<>();

    private CourseTestBuilder() {
    }

    public static CourseTestBuilder aCourse() {
        return new CourseTestBuilder();
    }

    public static CourseTestBuilder aCourseWithMaxEnrollments() {
        var students = IntStream.range(1,51)
                .mapToObj(i -> StudentTestBuilder.aStudent().id((long) i).build())
                .collect(Collectors.toList());
        return new CourseTestBuilder().students(students);
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
