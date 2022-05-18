package com.felipepossari.schoolregistration.application.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Student {
    private Long id;
    private String name;
    private String email;
    private List<Course> courses;

    public boolean isEnrolledInAnyCourse() {
        return courses != null && !courses.isEmpty();
    }

    public boolean isNotEnrolledIn(Course course) {
        return courses != null &&
                courses.stream()
                        .noneMatch(enrolledCourse -> enrolledCourse.getId().longValue() == course.getId().longValue());
    }

    public boolean hasReachMaxEnrollment(int studentMaxEnrollments) {
        return courses != null && courses.size() >= studentMaxEnrollments;
    }

    public void enroll(Course course) {
        courses.add(course);
    }

    public void cancelEnrollment(Course course) {
        courses.removeIf(enrolledCourse -> enrolledCourse.getId().longValue() == course.getId().longValue());
    }
}
