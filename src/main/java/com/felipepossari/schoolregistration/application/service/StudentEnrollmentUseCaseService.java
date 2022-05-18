package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.exception.EnrollmentException;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import com.felipepossari.schoolregistration.application.port.in.StudentEnrollmentUseCase;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_MAX_ENROLLMENTS_REACHED;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_MAX_ENROLLMENTS_REACHED;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentEnrollmentUseCaseService implements StudentEnrollmentUseCase {

    private static final int STUDENT_MAX_ENROLLMENTS = 5;
    private static final int COURSE_MAX_ENROLLMENTS = 50;

    private final StudentRepositoryPort studentRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;

    @Override
    public void enroll(Long studentId, Long courseId) {
        var student = retrieveStudent(studentId);
        var course = retrieveCourse(courseId);

        if(student.hasReachMaxEnrollment(STUDENT_MAX_ENROLLMENTS)){
            log.warn(STUDENT_MAX_ENROLLMENTS_REACHED.getMessage());
            throw new EnrollmentException(STUDENT_MAX_ENROLLMENTS_REACHED);
        }

        if(course.hasReachMaxEnrollment(COURSE_MAX_ENROLLMENTS)){
            log.warn(COURSE_MAX_ENROLLMENTS_REACHED.getMessage());
            throw new EnrollmentException(COURSE_MAX_ENROLLMENTS_REACHED);
        }

        if(student.isNotEnrolledIn(course)){
            student.enroll(course);
            studentRepositoryPort.update(student);
            log.info("Student enrolled. StudentId: {} CourseId: {}", studentId, courseId);
        }else{
            log.info("Student already enrolled. StudentId: {} CourseId: {}", studentId, courseId);
        }
    }

    @Override
    public void cancelEnrollment(Long studentId, Long courseId) {
        var student = retrieveStudent(studentId);
        var course = retrieveCourse(courseId);

        if(student.isNotEnrolledIn(course)){
            log.info("Student is not enrolled. StudentId: {} CourseId: {}", studentId, courseId);
        }else{
            student.cancelEnrollment(course);
            studentRepositoryPort.update(student);
            log.info("Enrollment canceled. StudentId: {} CourseId: {}", studentId, courseId);
        }
    }

    private Student retrieveStudent(Long studentId) {
        return studentRepositoryPort.findById(studentId)
                .orElseThrow(() ->
                {
                    log.warn(ErrorReason.STUDENT_NOT_FOUND.getMessage());
                    return new EntityNotFoundException(ErrorReason.STUDENT_NOT_FOUND);
                });
    }

    private Course retrieveCourse(Long courseId) {
        return courseRepositoryPort.findById(courseId)
                .orElseThrow(() ->
                {
                    log.warn(ErrorReason.COURSE_NOT_FOUND.getMessage());
                    return new EntityNotFoundException(ErrorReason.COURSE_NOT_FOUND);
                });
    }
}
