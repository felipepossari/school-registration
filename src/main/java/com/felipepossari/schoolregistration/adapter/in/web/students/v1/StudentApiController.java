package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.exception.InvalidRequestException;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import com.felipepossari.schoolregistration.application.port.in.StudentRegistrationUseCase;
import com.felipepossari.schoolregistration.application.port.in.StudentEnrollmentUseCase;
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
public class StudentApiController implements StudentApi {

    private final StudentApiRequestValidator requestValidator;
    private final StudentApiMapper mapper;

    private final StudentRegistrationUseCase useCase;
    private final StudentEnrollmentUseCase enrollmentUseCase;

    @Override
    public ResponseEntity<Void> post(StudentRequest studentRequest,
                                     BindingResult bindingResult,
                                     UriComponentsBuilder uriComponentsBuilder) {
        log.info("Posting student");
        validateRequest(bindingResult);
        var student = mapper.toDomain(studentRequest);
        student = useCase.create(student);
        var location = uriComponentsBuilder.path("/{id}").buildAndExpand(student.getId());
        return ResponseEntity.created(location.toUri()).build();
    }

    @Override
    public ResponseEntity<StudentResponse> getById(Long id) {
        log.info("Getting student by id. Id: {}", id);
        var student = useCase.read(id);
        return ResponseEntity.ok(mapper.toResponse(student));
    }

    @Override
    public ResponseEntity<List<StudentResponse>> get(int page, int size) {
        log.info("Getting students. Page: {}, Size: {}", page, size);
        var students = useCase.read(mapper.toFilter(page, size));
        return ResponseEntity.ok(mapper.toResponse(students));
    }

    @Override
    public ResponseEntity<Void> put(Long id,
                                    StudentRequest studentRequest,
                                    BindingResult bindingResult) {
        log.info("Updating student. Id: {}", id);
        validateRequest(bindingResult);
        useCase.update(mapper.toDomain(studentRequest, id));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("Deleting student. Id: {}", id);
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> enroll(Long id, Long courseId) {
        log.info("Student enrollment. StudentId: {} CourseId: {}", id, courseId);
        enrollmentUseCase.enroll(id, courseId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> cancelEnrollment(Long id, Long courseId) {
        log.info("Canceling student enrollment. StudentId: {} CourseId: {}", id, courseId);
        enrollmentUseCase.cancelEnrollment(id, courseId);
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
