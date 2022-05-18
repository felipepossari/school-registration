package com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CourseOverviewResponse {
    private Long id;
    private String name;
    private List<StudentOverviewResponse> students;
}
