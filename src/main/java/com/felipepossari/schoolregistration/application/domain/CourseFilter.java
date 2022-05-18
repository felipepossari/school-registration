package com.felipepossari.schoolregistration.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseFilter {
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int size = 20;
    private long studentId;
    private long courseId;
    private boolean withoutEnrollment;
}
