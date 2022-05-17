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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void readByFilterShouldReturnAListOfStudentsIfTheyDoExist() {
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
    void readByFilterShouldReturnAnEmptyListIfTheyDoNotExist() {
        // given
        StudentFilter filter = StudentFilter.builder().build();

        // and
        when(studentRepositoryPort.findByFilter(filter)).thenReturn(List.of());

        // when
        var actualStudents = service.read(filter);

        // then
        assertTrue(CollectionUtils.isEmpty(actualStudents));
    }

    @Test
    void updateShouldWorkFineIfStudentIsValid() {
        // given
        Student updatedStudent = StudentTestBuilder.aStudent().name("Felipe Updated").build();
        Student currentStudent = StudentTestBuilder.aStudent().build();

        // and
        when(studentRepositoryPort.findById(updatedStudent.getId())).thenReturn(Optional.of(currentStudent));
        when(studentRepositoryPort.findByEmailAndIdNot(updatedStudent.getEmail(), updatedStudent.getId()))
                .thenReturn(Optional.empty());
        doNothing().when(studentRepositoryPort).update(currentStudent);

        // when
        service.update(updatedStudent);

        // then
        verify(studentRepositoryPort).findById(updatedStudent.getId());
        verify(studentRepositoryPort).findByEmailAndIdNot(updatedStudent.getEmail(), updatedStudent.getId());
        verify(studentRepositoryPort, atMostOnce()).update(currentStudent);
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionIfStudentDoesNotExit(){
        // given
        Student updatedStudent = StudentTestBuilder.aStudent().name("Felipe Updated").build();

        // and
        when(studentRepositoryPort.findById(updatedStudent.getId())).thenReturn(Optional.empty());

        // when
        var ex = assertThrows(EntityNotFoundException.class, () -> service.update(updatedStudent));

        // then
        assertEquals(ErrorReason.STUDENT_NOT_FOUND.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort).findById(updatedStudent.getId());
        verify(studentRepositoryPort, never()).findByEmailAndIdNot(anyString(), anyLong());
        verify(studentRepositoryPort, never()).update(any(Student.class));
    }

    @Test
    void updateShouldThrowEntityRegisteredExceptionIfEmailUpdatedDoesBelongOtherUser(){
        // given
        Student updatedStudent = StudentTestBuilder.aStudent()
                .name("Felipe Updated")
                .email("felipeupdated@hotmail.com").build();
        Student currentUser = StudentTestBuilder.aStudent().build();
        Student otherUser = StudentTestBuilder.aStudent().email("felipeupdated@hotmail.com").build();

        // and
        when(studentRepositoryPort.findById(updatedStudent.getId())).thenReturn(Optional.of(currentUser));
        when(studentRepositoryPort.findByEmailAndIdNot(updatedStudent.getEmail(), updatedStudent.getId()))
                .thenReturn(Optional.of(otherUser));
        // when
        var ex = assertThrows(EntityRegisteredException.class, () -> service.update(updatedStudent));

        // then
        assertEquals(ErrorReason.EMAIL_ALREADY_REGISTERED.getCode(), ex.getErrorReason().getCode());
        verify(studentRepositoryPort).findById(updatedStudent.getId());
        verify(studentRepositoryPort).findByEmailAndIdNot(updatedStudent.getEmail(), updatedStudent.getId());
        verify(studentRepositoryPort, never()).update(any(Student.class));
    }
}
