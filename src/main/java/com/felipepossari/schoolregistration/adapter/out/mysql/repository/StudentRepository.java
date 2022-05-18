package com.felipepossari.schoolregistration.adapter.out.mysql.repository;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByEmail(String email);
    Optional<StudentEntity> findByEmailAndIdNot(String email, Long id);
}
