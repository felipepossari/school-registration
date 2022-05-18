package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/overview", produces = APPLICATION_JSON_VALUE)
public interface OverviewApi {

    String PAGE = "page";
    String SIZE = "size";
    String STUDENT_ID = "studentId";
    String COURSE_ID = "courseId";
    String WITHOUT_ENROLLMENT = "without-enrollment";
    String FALSE = "false";
    String PAGE_NUMBER = "0";
    String PAGE_SIZE = "20";
    String ZERO = "0";

    @GetMapping({"", "/students"})
    ResponseEntity<List<StudentOverviewResponse>> getStudents(
            @RequestParam(name = PAGE, defaultValue = PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = PAGE_SIZE) int size,
            @RequestParam(name = STUDENT_ID, defaultValue = ZERO) long studentId,
            @RequestParam(name = COURSE_ID, defaultValue = ZERO) long courseId,
            @RequestParam(name = WITHOUT_ENROLLMENT,
                    defaultValue = FALSE,
                    required = false) boolean withoutEnrollment
    );

    @GetMapping("/courses")
    ResponseEntity<List<CourseOverviewResponse>> getCourses(
            @RequestParam(name = PAGE, defaultValue = PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = PAGE_SIZE) int size,
            @RequestParam(name = STUDENT_ID, defaultValue = ZERO) long studentId,
            @RequestParam(name = COURSE_ID, defaultValue = ZERO) long courseId,
            @RequestParam(name = WITHOUT_ENROLLMENT,
                    defaultValue = FALSE,
                    required = false) boolean withoutEnrollment
    );
}
