package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.exception.EnrollmentException;
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
        return courseRepositoryPort.findByFilter(filter);
    }

    @Override
    public void update(Course course) {
        var currentCourse = retrieve(course.getId());
        validateUniqueUpdateName(course.getName(), course.getId());

        currentCourse.setName(course.getName());

        courseRepositoryPort.update(currentCourse);
    }

    @Override
    public void delete(Long id) {
        var course = retrieve(id);
        validateStudentEnrollment(course);
        courseRepositoryPort.delete(id);
    }

    private void validateStudentEnrollment(Course course) {
        if (course.hasAnyStudentEnrolled()) {
            log.warn(ErrorReason.COURSE_HAS_ENROLLED_STUDENT.getMessage());
            throw new EnrollmentException(ErrorReason.COURSE_HAS_ENROLLED_STUDENT);
        }
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

    private void validateUniqueUpdateName(String name, Long id) {
        if (courseRepositoryPort.findByNameAndIdNot(name, id).isPresent()) {
            log.warn(ErrorReason.COURSE_ALREADY_REGISTERED.getMessage());
            throw new EntityRegisteredException(ErrorReason.COURSE_ALREADY_REGISTERED);
        }
    }
}
