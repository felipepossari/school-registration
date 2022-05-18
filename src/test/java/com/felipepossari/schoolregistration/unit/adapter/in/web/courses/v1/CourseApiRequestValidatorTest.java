package com.felipepossari.schoolregistration.unit.adapter.in.web.courses.v1;

import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.CourseApiRequestValidator;
import com.felipepossari.schoolregistration.adapter.in.web.courses.v1.request.CourseRequest;
import com.felipepossari.schoolregistration.base.request.CourseRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_NAME_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.COURSE_NAME_NULL;
import static com.felipepossari.schoolregistration.base.DefaultConstants.COURSE_NAME_TOO_BIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CourseApiRequestValidatorTest {

    @InjectMocks
    CourseApiRequestValidator validator;

    @Test
    void validateShouldReturnNoErrorsIfRequestIsValid() {
        // given
        CourseRequest request = CourseRequestTestBuilder.aCourseRequest().build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateShouldReturnNameNullErrorIfNameIsNull() {
        // given
        CourseRequest request = CourseRequestTestBuilder.aCourseRequest().name("").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(COURSE_NAME_NULL.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnNameLengthInvalidErrorIfNameIsTooBig() {
        // given
        CourseRequest request = CourseRequestTestBuilder.aCourseRequest().name(COURSE_NAME_TOO_BIG).build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(COURSE_NAME_LENGTH_INVALID.getCode(), errors.getAllErrors().get(0).getCode());
    }
}
