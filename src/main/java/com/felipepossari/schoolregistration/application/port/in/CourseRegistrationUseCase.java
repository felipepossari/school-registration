package com.felipepossari.schoolregistration.application.port.in;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;

import java.util.List;

public interface CourseRegistrationUseCase {
    Course create(Course course);

    Course read(Long id);

    List<Course> read(CourseFilter filter);

    void update(Course course);

    void delete(Long id);
}
