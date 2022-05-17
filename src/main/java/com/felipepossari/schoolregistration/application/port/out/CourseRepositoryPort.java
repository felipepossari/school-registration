package com.felipepossari.schoolregistration.application.port.out;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;

import java.util.List;
import java.util.Optional;

public interface CourseRepositoryPort {
    Course create(Course course);

    Optional<Course> findById(Long id);

    Optional<Course> findByName(String name);

    Optional<Course> findByNameAndIdNot(String name, Long id);

    List<Course> findByFilter(CourseFilter filter);

    void update(Course course);

    void delete(Long id);
}
