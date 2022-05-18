package com.felipepossari.schoolregistration.application.service;

import com.felipepossari.schoolregistration.application.domain.Course;
import com.felipepossari.schoolregistration.application.domain.CourseFilter;
import com.felipepossari.schoolregistration.application.domain.Student;
import com.felipepossari.schoolregistration.application.domain.StudentFilter;
import com.felipepossari.schoolregistration.application.port.in.OverviewUseCase;
import com.felipepossari.schoolregistration.application.port.out.CourseRepositoryPort;
import com.felipepossari.schoolregistration.application.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverviewUseCaseService implements OverviewUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;

    @Override
    public List<Student> getStudentOverview(StudentFilter filter) {
        return List.of();
    }

    @Override
    public List<Course> getCoursesOverview(CourseFilter filter) {
        return courseRepositoryPort.findEagerByFilter(filter);
    }
}
