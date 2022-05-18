package com.felipepossari.schoolregistration.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_NAME_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_NAME_NULL;

@Component
public class CourseApiRequestValidator implements Validator {
    private static final int FIELD_LENGTH = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CourseRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest request = (CourseRequest) target;

        validateName(errors, request);
    }

    private void validateName(Errors errors, CourseRequest request) {
        if (StringUtils.isEmpty(request.getName())) {
            errors.reject(COURSE_NAME_NULL.getCode(), COURSE_NAME_NULL.getMessage());
        } else if (request.getName().length() > FIELD_LENGTH) {
            errors.reject(COURSE_NAME_LENGTH_INVALID.getCode(), COURSE_NAME_LENGTH_INVALID.getMessage());
        }
    }
}
