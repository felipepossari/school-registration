package com.felipepossari.schoolregistration.adapter.in.web.overview.v1;

import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.CourseOverviewResponse;
import com.felipepossari.schoolregistration.adapter.in.web.overview.v1.response.StudentOverviewResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/overview", produces = APPLICATION_JSON_VALUE)
@Api(value = "Overview")
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

    @ApiOperation(value = "Get the list of students with their enrolled courses")
    @GetMapping({"", "/students"})
    ResponseEntity<List<StudentOverviewResponse>> getStudents(
            @ApiParam(value = "Page number")
            @RequestParam(name = PAGE, defaultValue = PAGE_NUMBER) int page,

            @ApiParam(value = "Page size")
            @RequestParam(name = SIZE, defaultValue = PAGE_SIZE) int size,

            @ApiParam(value = "Student Id")
            @RequestParam(name = STUDENT_ID, defaultValue = ZERO) long studentId,

            @ApiParam(value = "Course Id")
            @RequestParam(name = COURSE_ID, defaultValue = ZERO) long courseId,

            @ApiParam(value = "true/false. Filter for students without enrollments")
            @RequestParam(name = WITHOUT_ENROLLMENT,
                    defaultValue = FALSE,
                    required = false) boolean withoutEnrollment
    );

    @ApiOperation(value = "Get the list of courses and the enrolled students")
    @GetMapping("/courses")
    ResponseEntity<List<CourseOverviewResponse>> getCourses(
            @ApiParam(value = "Page number")
            @RequestParam(name = PAGE, defaultValue = PAGE_NUMBER) int page,

            @ApiParam(value = "Page size")
            @RequestParam(name = SIZE, defaultValue = PAGE_SIZE) int size,

            @ApiParam(value = "Student Id")
            @RequestParam(name = STUDENT_ID, defaultValue = ZERO) long studentId,

            @ApiParam(value = "Course Id")
            @RequestParam(name = COURSE_ID, defaultValue = ZERO) long courseId,

            @ApiParam(value = "true/false. Filter for courses without students")
            @RequestParam(name = WITHOUT_ENROLLMENT,
                    defaultValue = FALSE,
                    required = false) boolean withoutEnrollment
    );
}
