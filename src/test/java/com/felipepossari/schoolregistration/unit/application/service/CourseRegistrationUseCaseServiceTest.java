package com.felipepossari.schoolregistration.unit.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import com.felipepossari.schoolregistration.application.service.CourseRegistrationUseCaseService;
import com.felipepossari.schoolregistration.unit.base.domain.CourseTestBuilder;
import com.felipepossari.schoolregistration.unit.base.domain.StudentTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    void createShouldCreateCourseIfNameIsNotRegistered(){
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
}
