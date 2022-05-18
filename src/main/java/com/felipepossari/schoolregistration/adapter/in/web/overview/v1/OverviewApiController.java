package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OverviewApiController implements OverviewApi {
    @Override
    public List<StudentOverviewResponse> getStudents(int page,
                                                     int size,
                                                     int studentId,
                                                     int courseId,
                                                     boolean withoutEnrollment) {
        return null;
    }

    @Override
    public List<CourseOverviewResponse> getCourses(int page,
                                                   int size,
                                                   int studentId,
                                                   int courseId,
                                                   boolean withoutEnrollment) {
        return null;
    }
}
