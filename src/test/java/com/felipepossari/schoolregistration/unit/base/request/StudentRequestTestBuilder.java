package com.felipepossari.schoolregistration.unit.base.request;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

public class StudentRequestTestBuilder {

    private String name = DefaultConstants.STUDENT_NAME;
    private String email = DefaultConstants.STUDENT_EMAIL;

    private StudentRequestTestBuilder() {
    }

    public static StudentRequestTestBuilder aStudentRequest() {
        return new StudentRequestTestBuilder();
    }


    public StudentRequestTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentRequestTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StudentRequest build() {
        return StudentRequest.builder()
                .email(email)
                .name(name)
                .build();
    }
}
