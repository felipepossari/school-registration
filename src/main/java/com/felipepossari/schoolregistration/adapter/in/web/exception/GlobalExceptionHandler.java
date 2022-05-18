package com.felipepossari.schoolregistration.adapter.in.web.exception;

import com.felipepossari.schoolregistration.adapter.in.web.exception.model.ErrorResponse;
import com.felipepossari.schoolregistration.application.exception.EnrollmentException;
import com.felipepossari.schoolregistration.application.exception.EntityNotFoundException;
import com.felipepossari.schoolregistration.application.exception.EntityRegisteredException;
import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String UNKNOWN_ERROR_MESSAGE = "An unexpected error happened";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(INTERNAL_SERVER_ERROR.toString())
                        .message(UNKNOWN_ERROR_MESSAGE)
                        .build());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<List<ErrorResponse>> handleInvalidRequestException(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(ex.getBindingResult().getAllErrors()));
    }

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<List<ErrorResponse>> handleEnrollmentException(EnrollmentException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(List.of(buildErrorResponse(ex.getErrorReason())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(List.of(buildErrorResponse(ex.getErrorReason())));
    }

    @ExceptionHandler(EntityRegisteredException.class)
    public ResponseEntity<List<ErrorResponse>> handleEntityRegisteredException(EntityRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(List.of(buildErrorResponse(ex.getErrorReason())));
    }

    private List<ErrorResponse> buildErrorResponse(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::buildErrorResponse)
                .collect(Collectors.toList());
    }

    private ErrorResponse buildErrorResponse(ObjectError error) {
        return ErrorResponse.builder()
                .code(error.getCode())
                .message(error.getDefaultMessage())
                .build();
    }

    private ErrorResponse buildErrorResponse(ErrorReason error) {
        return ErrorResponse.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .build();
    }
}
