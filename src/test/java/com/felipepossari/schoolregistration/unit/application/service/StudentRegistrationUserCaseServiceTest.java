package com.felipepossari.schoolregistration.unit.application.service;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import com.felipepossari.schoolregistration.application.service.StudentRegistrationUseCaseService;
import com.felipepossari.schoolregistration.unit.base.domain.StudentTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentRegistrationUserCaseServiceTest {

    @Mock
    StudentRepositoryPort studentRepositoryPort;

    @InjectMocks
    StudentRegistrationUseCaseService service;

    @Test
    void createShouldCreateStudentIfEmailIsNotRegistered() {
        // given
        Student student = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findByEmail(student.getEmail())).thenReturn(Optional.empty());
        when(studentRepositoryPort.create(student)).thenReturn(student);

        // when
        Student actualStudent = service.create(student);

        // then
        assertEquals(student, actualStudent);
        verify(studentRepositoryPort, times(1)).findByEmail(student.getEmail());
        verify(studentRepositoryPort, times(1)).create(student);
    }

    @Test
    void createShouldThrowEntityRegisteredExceptionIfEmailIsRegistered() {
        // given
        Student student = StudentTestBuilder.aStudent().build();
        Student studentRegistered = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findByEmail(student.getEmail())).thenReturn(Optional.of(studentRegistered));

        // when
        var ex = assertThrows(EntityRegisteredException.class, () -> service.create(student));

        // then
        assertEquals(ErrorReason.EMAIL_ALREADY_REGISTERED.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort, times(1)).findByEmail(student.getEmail());
        verify(studentRepositoryPort, never()).create(any(Student.class));
    }

    @Test
    void readByIdShouldReturnStudentIfHeOrSheDoesExist() {
        // given
        Student studentRequest = StudentTestBuilder.aStudent().build();
        Student expectedStudent = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findById(studentRequest.getId())).thenReturn(Optional.of(expectedStudent));

        // when
        var actualStudent = service.read(studentRequest.getId());

        // then
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void readByIdShouldThrowEntityNotFoundExceptionIfStudentDoesNotExist() {
        // given
        Student studentRequest = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findById(studentRequest.getId())).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.read(studentRequest.getId()));

        // then
        assertEquals(ErrorReason.STUDENT_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
    }

    @Test
    void readByFilterShouldReturnAListOfStudentsIfTheyDoExist(){
        // given
        StudentFilter filter = StudentFilter.builder().build();
        Student studentRequest = StudentTestBuilder.aStudent().build();
        var expectedStudents = List.of(studentRequest);

        // and
        when(studentRepositoryPort.findByFilter(filter)).thenReturn(expectedStudents);

        // when
        var actualStudents = service.read(filter);

        // then
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void readByFilterShouldReturnAnEmptyListIfTheyDoNotExist(){
        // given
        StudentFilter filter = StudentFilter.builder().build();

        // and
        when(studentRepositoryPort.findByFilter(filter)).thenReturn(List.of());

        // when
        var actualStudents = service.read(filter);

        // then
        assertTrue(CollectionUtils.isEmpty(actualStudents));
    }
}
