package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.port.in.StudentRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentRegistrationUseCaseService implements StudentRegistrationUseCase {
    @Override
    public Student create(Student student) {
        return null;
    }

    @Override
    public Student read(Long id) {
        return null;
    }

    @Override
    public List<Student> read(StudentFilter filter) {
        return null;
    }

    @Override
    public void update(Student student) {

    }

    @Override
    public void delete(Long id) {

    }
}
