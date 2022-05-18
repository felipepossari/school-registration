package com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseResponse {
    private Long id;
    private String name;
}
