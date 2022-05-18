package com.felipepossari.schoolregistration.unit.base.response;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

public class StudentResponseTestBuilder {

    private Long id = DefaultConstants.STUDENT_ID;
    private String name = DefaultConstants.STUDENT_NAME;
    private String email = DefaultConstants.STUDENT_EMAIL;

    private StudentResponseTestBuilder() {
    }

    public static StudentResponseTestBuilder aStudentResponse() {
        return new StudentResponseTestBuilder();
    }

    public StudentResponseTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudentResponseTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentResponseTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StudentResponse build() {
        return StudentResponse.builder()
                .id(id)
                .email(email)
                .name(name)
                .build();
    }
}
