package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import com.felipepossari.schoolregistration.application.port.in.StudentRegistrationUseCase;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentRegistrationUseCaseService implements StudentRegistrationUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    @Override
    public Student create(Student student) {
        validateUniqueEmail(student.getEmail());
        return studentRepositoryPort.create(student);
    }

    @Override
    public Student read(Long id) {
        return retrieve(id);
    }

    @Override
    public List<Student> read(StudentFilter filter) {
        return studentRepositoryPort.findByFilter(filter);
    }

    @Override
    public void update(Student student) {

    }

    @Override
    public void delete(Long id) {

    }

    private Student retrieve(Long id) {
        return studentRepositoryPort.findById(id)
                .orElseThrow(() ->
                {
                    log.warn(ErrorReason.STUDENT_NOT_FOUND.getMessage());
                    return new EntityNotFoundException(ErrorReason.STUDENT_NOT_FOUND);
                });
    }

    private void validateUniqueEmail(String email) {
        if (studentRepositoryPort.findByEmail(email).isPresent()) {
            log.warn(ErrorReason.EMAIL_ALREADY_REGISTERED.getMessage());
            throw new EntityRegisteredException(ErrorReason.EMAIL_ALREADY_REGISTERED);
        }
    }
}
