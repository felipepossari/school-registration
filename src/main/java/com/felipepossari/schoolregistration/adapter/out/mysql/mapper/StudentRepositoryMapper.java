package com.felipepossari.schoolregistration.adapter.out.mysql.mapper;

import com.felipepossari.schoolregistration.adapter.out.mysql.entity.CourseEntity;
import com.felipepossari.schoolregistration.adapter.out.mysql.entity.StudentEntity;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentRepositoryMapper {

    public StudentEntity toEntity(Student student) {
        return StudentEntity.builder()
                .id(student.getId())
                .email(student.getEmail())
                .name(student.getName())
                .courses(student.getCourses() != null ? mapEntityCoursesIds(student.getCourses()) : null)
                .build();
    }

    public Student toDomain(StudentEntity entity) {
        return Student.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .courses(entity.getCourses() != null ? mapDomainCoursesIds(entity.getCourses()) : null)
                .build();
    }

    public List<Student> toDomain(List<StudentEntity> students) {
        return students.stream().map(this::toDomain)
                .collect(Collectors.toList());
    }

    private List<CourseEntity> mapEntityCoursesIds(List<Course> courses) {
        return courses.stream()
                .map(it -> CourseEntity.builder().id(it.getId()).build())
                .collect(Collectors.toList());
    }


    private List<Course> mapDomainCoursesIds(List<CourseEntity> courses) {
        return courses.stream()
                .map(it -> Course.builder().id(it.getId()).build())
                .collect(Collectors.toList());
    }
}
