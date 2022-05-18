package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.exception.model.ErrorResponse;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/students", produces = APPLICATION_JSON_VALUE)
@Api(value = "Student")
public interface StudentApi {

    @ApiOperation(value = "Create a student")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student created"),
            @ApiResponse(code = 400, message = "Student request invalid", response = ErrorResponse.class, responseContainer = "List")
    })
    @PostMapping
    ResponseEntity<Void> post(
            @Valid @RequestBody StudentRequest studentRequest,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder);

    @ApiOperation(value = "Get a student by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "StudentResponse"),
            @ApiResponse(code = 404, message = "Student not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @GetMapping("/{id}")
    ResponseEntity<StudentResponse> getById(
            @PathVariable(name = "id") Long id);

    @ApiOperation(value = "Get the list of students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of StudentResponse")
    })
    @GetMapping
    ResponseEntity<List<StudentResponse>> get(
            @ApiParam(value = "Page number")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @ApiParam(value = "Page size")
            @RequestParam(name = "size", defaultValue = "20") int size);

    @ApiOperation(value = "Update a student")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Student request invalid", response = ErrorResponse.class, responseContainer = "List")
    })
    @PutMapping("/{id}")
    ResponseEntity<Void> put(
            @ApiParam(value = "Student Id")
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody StudentRequest studentRequest,
            BindingResult bindingResult);

    @ApiOperation(value = "Delete a student")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Student not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @ApiParam(value = "Student Id")
            @PathVariable(name = "id") Long id);

    @ApiOperation(value = "Enroll a student in a course")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Student or course not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @PutMapping("/{id}/enrollment/{courseId}")
    ResponseEntity<Void> enroll(
            @ApiParam(value = "Student Id")
            @PathVariable(name = "id") Long id,

            @ApiParam(value = "Course Id")
            @PathVariable(name = "courseId") Long courseId);

    @ApiOperation(value = "Cancel the student enrollment")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Student or course not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @DeleteMapping("/{id}/enrollment/{courseId}")
    ResponseEntity<Void> cancelEnrollment(
            @ApiParam(value = "Student Id")
            @PathVariable(name = "id") Long id,

            @ApiParam(value = "Course Id")
            @PathVariable(name = "courseId") Long courseId);
}
