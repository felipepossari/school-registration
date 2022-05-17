package com.felipepossari.schoolregistration.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_NULL;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_NAME_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_NAME_NULL;

@Component
public class StudentApiRequestValidator implements Validator {
    private static final int FIELD_LENGTH = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(StudentRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRequest request = (StudentRequest) target;

        validateName(errors, request);
        validateEmail(errors, request);
    }

    private void validateEmail(Errors errors, StudentRequest request) {
        if (StringUtils.isEmpty(request.getEmail())) {
            errors.reject(STUDENT_EMAIL_NULL.getCode(), STUDENT_EMAIL_NULL.getMessage());
        } else if (request.getEmail().length() > FIELD_LENGTH) {
            errors.reject(STUDENT_EMAIL_LENGTH_INVALID.getCode(), STUDENT_EMAIL_LENGTH_INVALID.getMessage());
        } else if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            errors.reject(STUDENT_EMAIL_INVALID.getCode(), STUDENT_EMAIL_INVALID.getMessage());
        }
    }

    private void validateName(Errors errors, StudentRequest request) {
        if (StringUtils.isEmpty(request.getName())) {
            errors.reject(STUDENT_NAME_NULL.getCode(), STUDENT_NAME_NULL.getMessage());
        } else if (request.getName().length() > FIELD_LENGTH) {
            errors.reject(STUDENT_NAME_LENGTH_INVALID.getCode(), STUDENT_NAME_LENGTH_INVALID.getMessage());
        }
    }
}
