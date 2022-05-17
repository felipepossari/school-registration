package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentApiController implements StudentApi {
    @Override
    public ResponseEntity<Void> post(StudentRequest studentRequest,
                                     BindingResult bindingResult,
                                     UriComponentsBuilder uriComponentsBuilder) {
        return null;
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
}
