package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.port.in.CourseRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseRegistrationUseCaseService implements CourseRegistrationUseCase {
    @Override
    public Course create(Course Course) {
        return null;
    }

    @Override
    public Course read(Long id) {
        return null;
    }

    @Override
    public List<Course> read(CourseFilter filter) {
        return null;
    }

    @Override
    public void update(Course Course) {

    }

    @Override
    public void delete(Long id) {

    }
}