package com.felipepossari.schoolregistration.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.exception.InvalidRequestException;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.response.CourseResponse;
import com.felipepossari.schoolregistration.application.port.in.CourseRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseApiController implements CourseApi {

    private final CourseApiRequestValidator requestValidator;
    private final CourseApiMapper mapper;
    private final CourseRegistrationUseCase useCase;

    @Override
    public ResponseEntity<Void> post(CourseRequest courseRequest,
                                     BindingResult bindingResult,
                                     UriComponentsBuilder uriComponentsBuilder) {
        log.info("Posting course");
        validateRequest(bindingResult);
        var course = mapper.toDomain(courseRequest);
        course = useCase.create(course);
        var location = uriComponentsBuilder.path("/{id}").buildAndExpand(course.getId());
        return ResponseEntity.created(location.toUri()).build();
    }

    @Override
    public ResponseEntity<CourseResponse> getById(Long id) {
        log.info("Getting course by id. Id: {}", id);
        var course = useCase.read(id);
        return ResponseEntity.ok(mapper.toResponse(course));
    }

    @Override
    public ResponseEntity<List<CourseResponse>> get(int page, int size) {
        log.info("Getting courses. Page: {}, Size: {}", page, size);
        var courses = useCase.read(mapper.toFilter(page, size));
        return ResponseEntity.ok(mapper.toResponse(courses));
    }

    @Override
    public ResponseEntity<Void> put(Long id,
                                    CourseRequest courseRequest,
                                    BindingResult bindingResult) {
        log.info("Updating course. Id: {}", id);
        validateRequest(bindingResult);
        useCase.update(mapper.toDomain(courseRequest, id));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("Deleting course. Id: {}", id);
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(requestValidator);
    }

    private void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
    }
}
