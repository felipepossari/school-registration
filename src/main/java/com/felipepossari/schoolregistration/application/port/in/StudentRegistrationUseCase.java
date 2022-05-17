package com.felipepossari.schoolregistration.application.port.in;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;

import java.util.List;

public interface StudentRegistrationUseCase {
    Student create(Student student);

    Student read(Long id);

    List<Student> read(StudentFilter filter);

    void update(Student student);

    void delete(Long id);
}
