package com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CourseRequest {
    private String name;
}
