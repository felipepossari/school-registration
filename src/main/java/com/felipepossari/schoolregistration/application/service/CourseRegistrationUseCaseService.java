package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.in.CourseRegistrationUseCase;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseRegistrationUseCaseService implements CourseRegistrationUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    @Override
    public Course create(Course course) {
        validateUniqueName(course.getName());
        return courseRepositoryPort.create(course);
    }

    @Override
    public Course read(Long id) {
        return retrieve(id);
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

    private Course retrieve(Long id) {
        return courseRepositoryPort.findById(id)
                .orElseThrow(() ->
                {
                    log.warn(ErrorReason.COURSE_NOT_FOUND.getMessage());
                    return new EntityNotFoundException(ErrorReason.COURSE_NOT_FOUND);
                });
    }

    private void validateUniqueName(String name) {
        if (courseRepositoryPort.findByName(name).isPresent()) {
            log.warn(ErrorReason.COURSE_ALREADY_REGISTERED.getMessage());
            throw new EntityRegisteredException(ErrorReason.COURSE_ALREADY_REGISTERED);
        }
    }
}
