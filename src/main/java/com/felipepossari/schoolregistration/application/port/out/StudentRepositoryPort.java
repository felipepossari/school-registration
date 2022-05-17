package com.felipepossari.schoolregistration.application.port.out;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;

import java.util.List;

public interface StudentRepositoryPort {
    Student create(Student student);

    Student findById(Long id);

    Student findByEmail(String email);

    Student findByEmailAndIdNot(String email, Long id);

    List<Student> findByFilter(StudentFilter filter);

    void update(Student student);

    void delete(Long id);
}
