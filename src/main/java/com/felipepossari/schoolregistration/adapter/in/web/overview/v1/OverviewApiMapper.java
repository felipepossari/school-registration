package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OverviewApiMapper {

    public StudentOverviewResponse toResponse(Student student) {
        return StudentOverviewResponse.builder()
                .email(student.getEmail())
                .id(student.getId())
                .name(student.getName())
                .courses(student.getCourses() != null ? this.toCoursesResponse(student.getCourses()) : null)
                .build();
    }

    public CourseOverviewResponse toResponse(Course course) {
        return CourseOverviewResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .students(course.getStudents() != null ? this.toStudentsResponse(course.getStudents()) : null)
                .build();
    }

    public List<CourseOverviewResponse> toCoursesResponse(List<Course> courses) {
        return courses.stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<StudentOverviewResponse> toStudentsResponse(List<Student> students) {
        return students.stream().map(this::toResponse)
                .collect(Collectors.toList());
    }
}
