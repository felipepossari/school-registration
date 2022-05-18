package com.felipepossari.schoolregistration.application.port.in;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;

import java.util.List;

public interface OverviewUseCase {

    List<Student> getStudentOverview(StudentFilter filter);

    List<Course> getCoursesOverview(CourseFilter filter);
}
