package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentApiMapper {
    public Student toDomain(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .build();
    }

    public StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .email(student.getEmail())
                .id(student.getId())
                .name(student.getName())
                .build();
    }

    public List<StudentResponse> toResponse(List<Student> students) {
        return students.stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public StudentFilter toFilder(int page, int size) {
        return StudentFilter.builder()
                .page(page)
                .size(size)
                .build();
    }
}
