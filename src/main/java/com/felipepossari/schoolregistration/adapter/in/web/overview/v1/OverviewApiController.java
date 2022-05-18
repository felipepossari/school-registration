package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import com.felipepossari.schoolregistration.application.port.in.OverviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OverviewApiController implements OverviewApi {

    private final OverviewUseCase overviewUseCase;
    private final OverviewApiMapper mapper;

    @Override
    public ResponseEntity<List<StudentOverviewResponse>> getStudents(int page,
                                                                     int size,
                                                                     long studentId,
                                                                     long courseId,
                                                                     boolean withoutEnrollment) {
        return null;
    }

    @Override
    public ResponseEntity<List<CourseOverviewResponse>> getCourses(int page,
                                                                   int size,
                                                                   long studentId,
                                                                   long courseId,
                                                                   boolean withoutEnrollment) {
        var filter = mapper.toFilter(page, size, studentId, courseId, withoutEnrollment);
        var courses = overviewUseCase.getCoursesOverview(filter);
        return ResponseEntity.ok(mapper.toCoursesResponse(courses));
    }
}
