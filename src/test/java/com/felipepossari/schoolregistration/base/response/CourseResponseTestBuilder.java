package com.felipepossari.schoolregistration.base.response;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
import com.felipepossari.schoolregistration.base.DefaultConstants;

public class CourseResponseTestBuilder {

    private Long id = DefaultConstants.COURSE_ID;
    private String name = DefaultConstants.COURSE_NAME;

    private CourseResponseTestBuilder() {
    }

    public static CourseResponseTestBuilder aCourseResponse() {
        return new CourseResponseTestBuilder();
    }

    public CourseResponseTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CourseResponseTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CourseResponse build() {
        return CourseResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
