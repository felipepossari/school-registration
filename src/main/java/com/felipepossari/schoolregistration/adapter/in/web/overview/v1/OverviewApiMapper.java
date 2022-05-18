package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
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

    public CourseFilter toCourseFilter(int page, int size, long studentId, long courseId, boolean withoutEnrollment) {
        return CourseFilter.builder()
                .page(page)
                .size(size)
                .courseId(courseId)
                .studentId(studentId)
                .withoutEnrollment(withoutEnrollment)
                .build();
    }

    public StudentFilter toStudentFilter(int page, int size, long studentId, long courseId, boolean withoutEnrollment) {
        return StudentFilter.builder()
                .page(page)
                .size(size)
                .courseId(courseId)
                .studentId(studentId)
                .withoutEnrollment(withoutEnrollment)
                .build();
    }
}
