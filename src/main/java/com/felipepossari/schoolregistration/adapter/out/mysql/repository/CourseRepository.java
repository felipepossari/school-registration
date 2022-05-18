package com.felipepossari.schoolregistration.adapter.out.mysql.repository;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    Optional<CourseEntity> findByName(String name);

    Optional<CourseEntity> findByNameAndIdNot(String name, Long id);

}
