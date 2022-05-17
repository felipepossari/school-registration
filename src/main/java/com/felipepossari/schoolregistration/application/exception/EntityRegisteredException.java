package com.felipepossari.schoolregistration.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntityRegisteredException extends RuntimeException {

    private final ErrorReason errorReason;
}
