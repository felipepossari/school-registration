package com.felipepossari.schoolregistration.application.port.out;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;

import java.util.List;
import java.util.Optional;

public interface StudentRepositoryPort {
    Student create(Student student);

    Optional<Student> findById(Long id);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByEmailAndIdNot(String email, Long id);

    List<Student> findByFilter(StudentFilter filter);

    void update(Student student);

    void delete(Long id);
}
