package com.felipepossari.schoolregistration.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EnrollmentException extends RuntimeException {
    private final ErrorReason errorReason;
}
