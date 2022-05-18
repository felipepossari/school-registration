package com.felipepossari.schoolregistration.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
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
public interface CourseApi {

    @PostMapping
    ResponseEntity<Void> post(
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder);

    @GetMapping("/{id}")
    ResponseEntity<CourseResponse> getById(
            @PathVariable(name = "id") Long id);

    @GetMapping
    ResponseEntity<List<CourseResponse>> get(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size);

    @PutMapping("/{id}")
    ResponseEntity<Void> put(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody CourseRequest courseRequest,
            BindingResult bindingResult);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable(name = "id") Long id);
}
