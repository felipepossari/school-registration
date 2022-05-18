package com.felipepossari.schoolregistration.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseApiMapper {
    public Course toDomain(CourseRequest courseRequest) {
        return Course.builder()
                .name(courseRequest.getName())
                .build();
    }

    public CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

    public List<CourseResponse> toResponse(List<Course> courses) {
        return courses.stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CourseFilter toFilter(int page, int size) {
        return CourseFilter.builder()
                .page(page)
                .size(size)
                .build();
    }
}
