package com.felipepossari.schoolregistration.adapter.in.out.mysql;

import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentRepositoryPortService implements StudentRepositoryPort {
    @Override
    public Student create(Student student) {
        return null;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByEmailAndIdNot(String email, Long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findByFilter(StudentFilter filter) {
        return null;
    }

    @Override
    public void update(Student student) {

    }

    @Override
    public void delete(Long id) {

    }
}
