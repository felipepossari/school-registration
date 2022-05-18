package com.felipepossari.schoolregistration.base.domain;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.base.DefaultConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentTestBuilder {

    private Long id = DefaultConstants.STUDENT_ID;
    private String name = DefaultConstants.STUDENT_NAME;
    private String email = DefaultConstants.STUDENT_EMAIL;
    private List<Course> courses = new ArrayList<>();

    private StudentTestBuilder() {
    }

    public static StudentTestBuilder aStudent() {
        return new StudentTestBuilder();
    }

    public static StudentTestBuilder aStudentWithMaxEnrollments() {
        var courses = IntStream.range(1,6).mapToObj(it -> CourseTestBuilder.aCourse().id((long) it).build())
                .collect(Collectors.toList());

        return new StudentTestBuilder().courses(courses);
    }

    public StudentTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudentTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentTestBuilder name(String name) {
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
