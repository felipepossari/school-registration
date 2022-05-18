package com.felipepossari.schoolregistration.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.exception.model.ErrorResponse;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
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

@RequestMapping(value = "/v1/courses", produces = APPLICATION_JSON_VALUE)
@Api(value = "Course")
public interface CourseApi {

    @ApiOperation(value = "Create a course")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Course created"),
            @ApiResponse(code = 400, message = "Course request invalid", response = ErrorResponse.class, responseContainer = "List")
    })
    @PostMapping
    ResponseEntity<Void> post(
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder);

    @ApiOperation(value = "Get a course by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CourseResponse"),
            @ApiResponse(code = 404, message = "Course not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @GetMapping("/{id}")
    ResponseEntity<CourseResponse> getById(
            @ApiParam(value = "Course Id")
            @PathVariable(name = "id") Long id);

    @ApiOperation(value = "Get the list of students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of CourseResponse")
    })
    @GetMapping
    ResponseEntity<List<CourseResponse>> get(
            @ApiParam(value = "Page number")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @ApiParam(value = "Page size")
            @RequestParam(name = "size", defaultValue = "20") int size);

    @ApiOperation(value = "Update a course")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Course request invalid", response = ErrorResponse.class, responseContainer = "List")
    })
    @PutMapping("/{id}")
    ResponseEntity<Void> put(
            @ApiParam(value = "Course Id")
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult);

    @ApiOperation(value = "Delete a course")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Course not found", response = ErrorResponse.class, responseContainer = "List")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @ApiParam(value = "Course Id")
            @PathVariable(name = "id") Long id);
}
