package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.port.in.OverviewUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverviewUseCaseService implements OverviewUseCase {
    @Override
    public List<Student> getStudentOverview(StudentFilter filter) {
        return null;
    }

    @Override
    public List<Course> getCoursesOverview(CourseFilter filter) {
        return null;
    }
}
