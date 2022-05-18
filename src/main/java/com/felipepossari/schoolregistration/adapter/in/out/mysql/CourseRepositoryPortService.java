package com.felipepossari.schoolregistration.adapter.in.out.mysql;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseRepositoryPortService implements CourseRepositoryPort {
    @Override
    public Course create(Course course) {
        return null;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> findByNameAndIdNot(String name, Long id) {
        return Optional.empty();
    }

    @Override
    public List<Course> findByFilter(CourseFilter filter) {
        return null;
    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Long id) {

    }
}
