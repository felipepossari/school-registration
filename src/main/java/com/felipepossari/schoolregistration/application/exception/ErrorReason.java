package com.felipepossari.schoolregistration.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorReason {

    EMAIL_ALREADY_REGISTERED("S001", "Email already registered"),
    STUDENT_NOT_FOUND("S002", "Student not found"),
    STUDENT_ENROLLED("S003", "Student enrolled in a course"),

    STUDENT_NAME_NULL("S010", "Student name cannot be null or empty"),
    STUDENT_NAME_LENGTH_INVALID("S011", "Student name must have less than 255 characters"),

    STUDENT_EMAIL_NULL("S012", "Student email cannot be null or empty"),
    STUDENT_EMAIL_LENGTH_INVALID("S013", "Student email must have less than 255 characters"),
    STUDENT_EMAIL_INVALID("S014", "Student email invalid"),

    COURSE_ALREADY_REGISTERED("C001", "Course already registered"),
    COURSE_NOT_FOUND("C002", "Course not found"),
    COURSE_HAS_ENROLLED_STUDENT("C003", "Course has students enrolled");

    private String code;
    private String message;
}
