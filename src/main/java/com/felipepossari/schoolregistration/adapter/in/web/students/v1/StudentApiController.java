package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.in.web.exception.InvalidRequestException;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import com.felipepossari.schoolregistration.application.port.in.StudentRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentApiController implements StudentApi {

    private final StudentApiRequestValidator requestValidator;
    private final StudentApiMapper mapper;
    private final StudentRegistrationUseCase useCase;

    @Override
    public ResponseEntity<Void> post(StudentRequest studentRequest,
                                     BindingResult bindingResult,
                                     UriComponentsBuilder uriComponentsBuilder) {
        validateRequest(bindingResult);
        var student = mapper.toDomain(studentRequest);
        student = useCase.create(student);
        var location = uriComponentsBuilder.path("/{id}").buildAndExpand(student.getId());
        return ResponseEntity.created(location.toUri()).build();
    }

    @Override
    public ResponseEntity<StudentResponse> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<StudentResponse>> get(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<Void> put(Long id,
                                    StudentRequest studentRequest,
                                    BindingResult bindingResult) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) { webDataBinder.setValidator(requestValidator);}

    private void validateRequest(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException(bindingResult);
        }
    }
}
