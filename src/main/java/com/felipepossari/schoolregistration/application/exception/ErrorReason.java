package com.felipepossari.schoolregistration.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorReason {

    EMAIL_ALREADY_REGISTERED("S001", "Email already registered"),
    STUDENT_NOT_FOUND("S002", "Student not found"),
    STUDENT_ENROLLED("S003", "Student enrolled in a course");

    private String code;
    private String message;
}
