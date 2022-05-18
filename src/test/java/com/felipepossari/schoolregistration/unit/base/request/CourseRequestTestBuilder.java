package com.felipepossari.schoolregistration.unit.base.request;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.unit.base.DefaultConstants;

public class CourseRequestTestBuilder {

    private String name = DefaultConstants.STUDENT_NAME;

    private CourseRequestTestBuilder() {
    }

    public static CourseRequestTestBuilder aCourseRequest() {
        return new CourseRequestTestBuilder();
    }

    public CourseRequestTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CourseRequest build() {
        return CourseRequest.builder()
                .name(name)
                .build();
    }
}
