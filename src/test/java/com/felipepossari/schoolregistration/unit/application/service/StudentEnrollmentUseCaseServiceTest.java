package com.felipepossari.schoolregistration.unit.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.exception.EnrollmentException;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import com.felipepossari.schoolregistration.application.service.StudentEnrollmentUseCaseService;
import com.felipepossari.schoolregistration.unit.base.domain.CourseTestBuilder;
import com.felipepossari.schoolregistration.unit.base.domain.StudentTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_MAX_ENROLLMENTS_REACHED;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_MAX_ENROLLMENTS_REACHED;
import static com.felipepossari.schoolregistration.unit.base.DefaultConstants.COURSE_ID;
import static com.felipepossari.schoolregistration.unit.base.DefaultConstants.STUDENT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentEnrollmentUseCaseServiceTest {

    @Mock
    StudentRepositoryPort studentRepositoryPort;

    @Mock
    CourseRepositoryPort courseRepositoryPort;

    @InjectMocks
    StudentEnrollmentUseCaseService service;

    @Test
    void enrollShouldWorkFineIfStudentAndCourseAreValid() {
        // given
        Student student = StudentTestBuilder.aStudent().build();
        Course course = CourseTestBuilder.aCourse().build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));
        doNothing().when(studentRepositoryPort).update(student);

        // when
        service.enroll(STUDENT_ID, COURSE_ID);

        // then
        assertTrue(
                student.getCourses()
                        .stream()
                        .anyMatch(c -> c.getId().longValue() == course.getId().longValue()));
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, atMostOnce()).update(student);
    }

    @Test
    void enrollShouldNotEnrollStudentIfHeOrSheIsAlreadyEnrolled() {
        // given
        Course course = CourseTestBuilder.aCourse().build();
        Student student = StudentTestBuilder.aStudent().courses(List.of(course)).build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));

        // when
        service.enroll(STUDENT_ID, COURSE_ID);

        // then
        assertTrue(
                student.getCourses()
                        .stream()
                        .anyMatch(c -> c.getId().longValue() == course.getId().longValue()));
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, never()).update(student);
    }

    @Test
    void enrollShouldThrowEntityNotFoundIfStudentDoesNotExist() {
        // given
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.enroll(STUDENT_ID, COURSE_ID));

        // then
        assertEquals(ErrorReason.STUDENT_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort, never()).update(any(Student.class));
    }

    @Test
    void enrollShouldThrowEntityNotFoundIfCourseDoesNotExist() {
        // given
        Student student = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.enroll(STUDENT_ID, COURSE_ID));

        // then
        assertEquals(ErrorReason.COURSE_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort, never()).update(any(Student.class));
    }

    @Test
    void enrollShouldThrowEnrollmentExceptionIfStudentDoesReachMaxEnrollments() {
        // given
        Student student = StudentTestBuilder.aStudentWithMaxEnrollments().build();
        Course course = CourseTestBuilder.aCourse().build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));

        // when
        var ex = assertThrows(EnrollmentException.class, () -> service.enroll(STUDENT_ID, COURSE_ID));

        // then
        assertEquals(STUDENT_MAX_ENROLLMENTS_REACHED.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, never()).update(student);
    }

    @Test
    void enrollShouldThrowEnrollmentExceptionIfCourseDoesReachMaxEnrollments() {
        // given
        Student student = StudentTestBuilder.aStudent().build();
        Course course = CourseTestBuilder.aCourseWithMaxEnrollments().build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));

        // when
        var ex = assertThrows(EnrollmentException.class, () -> service.enroll(STUDENT_ID, COURSE_ID));

        // then
        assertEquals(COURSE_MAX_ENROLLMENTS_REACHED.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, never()).update(student);
    }

    @Test
    void cancelEnrollmentShouldWorkFineIfStudentAndCourseAreValid() {
        // given
        Course course = CourseTestBuilder.aCourse().build();
        Student student = StudentTestBuilder.aStudent()
                .courses(Stream.of(course).collect(Collectors.toList()))
                .build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));
        doNothing().when(studentRepositoryPort).update(student);

        // when
        service.cancelEnrollment(STUDENT_ID, COURSE_ID);

        // then
        assertTrue(
                student.getCourses()
                        .stream()
                        .noneMatch(c -> c.getId().longValue() == course.getId().longValue()));
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, atMostOnce()).update(student);
    }

    @Test
    void cancelEnrollmentShouldDoNothingIfStudentIsNotEnrolled() {
        // given
        Course course = CourseTestBuilder.aCourse().build();
        Student student = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));

        // when
        service.cancelEnrollment(STUDENT_ID, COURSE_ID);

        // then
        assertTrue(
                student.getCourses()
                        .stream()
                        .noneMatch(c -> c.getId().longValue() == course.getId().longValue()));
        verify(studentRepositoryPort).findById(STUDENT_ID);
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(studentRepositoryPort, never()).update(student);
    }
}
