package com.felipepossari.schoolregistration.adapter.in.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class InvalidRequestException extends RuntimeException {
    private final transient BindingResult bindingResult;
}
