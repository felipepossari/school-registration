package com.felipepossari.schoolregistration.unit.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.exception.EnrollmentException;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import com.felipepossari.schoolregistration.application.service.CourseRegistrationUseCaseService;
import com.felipepossari.schoolregistration.base.domain.CourseTestBuilder;
import com.felipepossari.schoolregistration.base.domain.StudentTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.felipepossari.schoolregistration.base.DefaultConstants.COURSE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseRegistrationUseCaseServiceTest {

    @Mock
    CourseRepositoryPort courseRepositoryPort;

    @InjectMocks
    CourseRegistrationUseCaseService service;

    @Test
    void createShouldCreateCourseIfNameIsNotRegistered() {
        // given
        Course course = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findByName(course.getName())).thenReturn(Optional.empty());
        when(courseRepositoryPort.create(course)).thenReturn(course);

        // when
        Course actualCourse = service.create(course);

        // then
        assertEquals(course, actualCourse);
        verify(courseRepositoryPort, times(1)).findByName(course.getName());
        verify(courseRepositoryPort, times(1)).create(course);
    }

    @Test
    void createShouldThrowEntityRegisteredExceptionIfNameIsRegistered() {
        // given
        Course course = CourseTestBuilder.aCourse().build();
        Course courseRegistered = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findByName(course.getName())).thenReturn(Optional.of(courseRegistered));

        // when
        var ex = assertThrows(EntityRegisteredException.class, () -> service.create(course));

        // then
        assertEquals(ErrorReason.COURSE_ALREADY_REGISTERED.getCode(), ex.getErrorReason().getCode());
        verify(courseRepositoryPort, times(1)).findByName(course.getName());
        verify(courseRepositoryPort, never()).create(any(Course.class));
    }

    @Test
    void readByIdShouldReturnCourseIfItDoesExist() {
        // given
        Course courseRequest = CourseTestBuilder.aCourse().build();
        Course expectedCourse = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findById(courseRequest.getId())).thenReturn(Optional.of(expectedCourse));

        // when
        var actualCourse = service.read(courseRequest.getId());

        // then
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void readByIdShouldThrowEntityNotFoundExceptionIfCourseDoesNotExist() {
        // given
        Course courseRequest = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findById(courseRequest.getId())).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.read(courseRequest.getId()));

        // then
        assertEquals(ErrorReason.COURSE_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
    }

    @Test
    void updateShouldWorkFineIfCourseIsValid() {
        // given
        Course updatedCourse = CourseTestBuilder.aCourse().name("Course Updated").build();
        Course currentCourse = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findById(updatedCourse.getId())).thenReturn(Optional.of(currentCourse));
        when(courseRepositoryPort.findByNameAndIdNot(updatedCourse.getName(), updatedCourse.getId()))
                .thenReturn(Optional.empty());
        doNothing().when(courseRepositoryPort).update(currentCourse);

        // when
        service.update(updatedCourse);

        // then
        verify(courseRepositoryPort).findById(updatedCourse.getId());
        verify(courseRepositoryPort).findByNameAndIdNot(updatedCourse.getName(), updatedCourse.getId());
        verify(courseRepositoryPort, atMostOnce()).update(currentCourse);
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionIfCourseDoesNotExist() {
        // given
        Course updatedCourse = CourseTestBuilder.aCourse().name("Course Updated").build();

        // and
        when(courseRepositoryPort.findById(updatedCourse.getId())).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.update(updatedCourse));

        // then
        assertEquals(ErrorReason.COURSE_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
        verify(courseRepositoryPort).findById(updatedCourse.getId());
        verify(courseRepositoryPort, never()).findByNameAndIdNot(anyString(), anyLong());
        verify(courseRepositoryPort, never()).update(any(Course.class));
    }

    @Test
    void updateShouldThrowEntityRegisteredExceptionIfNameUpdatedDoesBelongOtherUser() {
        // given
        Course updatedCourse = CourseTestBuilder.aCourse()
                .name("Course Updated").build();
        Course currentUser = CourseTestBuilder.aCourse().build();
        Course otherUser = CourseTestBuilder.aCourse().name("Course Updated").build();

        // and
        when(courseRepositoryPort.findById(updatedCourse.getId())).thenReturn(Optional.of(currentUser));
        when(courseRepositoryPort.findByNameAndIdNot(updatedCourse.getName(), updatedCourse.getId()))
                .thenReturn(Optional.of(otherUser));
        // when
        var ex = assertThrows(EntityRegisteredException.class, () -> service.update(updatedCourse));

        // then
        assertEquals(ErrorReason.COURSE_ALREADY_REGISTERED.getCode(), ex.getErrorReason().getCode());
        verify(courseRepositoryPort).findById(updatedCourse.getId());
        verify(courseRepositoryPort).findByNameAndIdNot(updatedCourse.getName(), updatedCourse.getId());
        verify(courseRepositoryPort, never()).update(any(Course.class));
    }

    @Test
    void deleteShouldWorkFineIfCourseDoesExistAndItHasNoEnrolledStudent() {
        // given
        Course course = CourseTestBuilder.aCourse().build();

        // and
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));
        doNothing().when(courseRepositoryPort).delete(COURSE_ID);

        // when
        service.delete(COURSE_ID);

        // then
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(courseRepositoryPort, atMostOnce()).delete(COURSE_ID);
    }

    @Test
    void deleteShouldThrowEntityNotFoundIfCourseDoesNotExist() {
        // given
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.delete(COURSE_ID));

        // then
        assertEquals(ErrorReason.COURSE_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(courseRepositoryPort, never()).delete(COURSE_ID);
    }

    @Test
    void deleteShouldThrowEnrollmentExceptionIfCourseIsEnrolledInAnyCourse() {
        // given
        Student student = StudentTestBuilder.aStudent().build();
        Course course = CourseTestBuilder.aCourse().students(List.of(student)).build();

        // and
        when(courseRepositoryPort.findById(COURSE_ID)).thenReturn(Optional.of(course));

        // when
        var ex = assertThrows(EnrollmentException.class, () -> service.delete(COURSE_ID));

        // then
        assertEquals(ErrorReason.COURSE_HAS_ENROLLED_STUDENT.getCode(), ex.getErrorReason().getCode());
        verify(courseRepositoryPort).findById(COURSE_ID);
        verify(courseRepositoryPort, never()).delete(COURSE_ID);
    }
}
