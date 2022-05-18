package com.felipepossari.schoolregistration.adapter.in.out.mysql.repository;

import com.felipepossari.schoolregistration.adapter.in.out.mysql.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
