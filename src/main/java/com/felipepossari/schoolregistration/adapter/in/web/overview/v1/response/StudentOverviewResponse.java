package com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentOverviewResponse {
    private Long id;
    private String name;
    private String email;
    private List<CourseOverviewResponse> courses;
}
