package com.felipepossari.schoolregistration.adapter.out.mysql.mapper;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.CourseEntity;
import com.felipepossari.schoolregistration.adapter.out.mysql.entity.StudentEntity;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseRepositoryMapper {

    public CourseEntity toEntity(Course course) {
        return CourseEntity.builder()
                .id(course.getId())
                .name(course.getName())
                .students(course.getStudents() != null ? mapEntityCoursesIds(course.getStudents()) : null)
                .build();
    }

    public Course toDomain(CourseEntity entity) {
        return Course.builder()
                .id(entity.getId())
                .name(entity.getName())
                .students(entity.getStudents() != null ? mapDomainCoursesIds(entity.getStudents()) : null)
                .build();
    }

    public List<Course> toDomain(List<CourseEntity> courses) {
        return courses.stream().map(this::toDomain)
                .collect(Collectors.toList());
    }

    private List<StudentEntity> mapEntityCoursesIds(List<Student> courses) {
        return courses.stream()
                .map(it -> StudentEntity.builder().id(it.getId()).build())
                .collect(Collectors.toList());
    }


    private List<Student> mapDomainCoursesIds(List<StudentEntity> courses) {
        return courses.stream()
                .map(it -> Student.builder().id(it.getId()).build())
                .collect(Collectors.toList());
    }
}
