package com.felipepossari.schoolregistration.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorReason errorReason;
}
