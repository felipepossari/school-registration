package com.felipepossari.schoolregistration.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorReason {

    EMAIL_ALREADY_REGISTERED("S001", "Email already registered"),
    STUDENT_NOT_FOUND("S002", "Student not found"),
    STUDENT_ENROLLED("S003", "Student enrolled in a course"),

    STUDENT_NAME_NULL("S004", "Student name cannot be null or empty"),
    STUDENT_NAME_LENGTH_INVALID("S005", "Student name must have less than 255 characters"),

    STUDENT_EMAIL_NULL("S006", "Student email cannot be null or empty"),
    STUDENT_EMAIL_LENGTH_INVALID("S007", "Student email must have less than 255 characters"),
    STUDENT_EMAIL_INVALID("S008", "Student email invalid"),

    COURSE_ALREADY_REGISTERED("C001", "Course already registered"),
    COURSE_NOT_FOUND("C002", "Course not found"),
    COURSE_HAS_ENROLLED_STUDENT("C003", "Course has students enrolled"),

    COURSE_NAME_NULL("C004", "Course name cannot be null or empty"),
    COURSE_NAME_LENGTH_INVALID("C005", "Course name must have less than 255 characters");

    private String code;
    private String message;
}
