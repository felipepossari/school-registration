package com.felipepossari.schoolregistration.unit.adapter.in.web.students.v1;

import com.felipepossari.schoolregistration.adapter.in.web.students.v1.StudentApiRequestValidator;
import com.felipepossari.schoolregistration.adapter.in.web.students.v1.request.StudentRequest;
import com.felipepossari.schoolregistration.unit.base.request.StudentRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_EMAIL_NULL;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_NAME_LENGTH_INVALID;
import static com.felipepossari.schoolregistration.application.exception.ErrorReason.STUDENT_NAME_NULL;
import static com.felipepossari.schoolregistration.unit.base.DefaultConstants.STUDENT_EMAIL_TOO_BIG;
import static com.felipepossari.schoolregistration.unit.base.DefaultConstants.STUDENT_NAME_TOO_BIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StudentApiRequestValidatorTest {

    @InjectMocks
    StudentApiRequestValidator validator;

    @Test
    void validateShouldReturnNoErrorsIfRequestIsValid() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateShouldReturnEmailNullErrorIfEmailIsNull() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().email("").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(STUDENT_EMAIL_NULL.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnEmailLengthInvalidErrorIfEmailIsTooBig() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().email(STUDENT_EMAIL_TOO_BIG).build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(STUDENT_EMAIL_LENGTH_INVALID.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnEmailInvalidErrorIfEmailIsInvalid() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().email("felipe@hotmail").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(STUDENT_EMAIL_INVALID.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnNameNullErrorIfNameIsNull() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().name("").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(STUDENT_NAME_NULL.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnNameLengthInvalidErrorIfNameIsTooBig() {
        // given
        StudentRequest request = StudentRequestTestBuilder.aStudentRequest().name(STUDENT_NAME_TOO_BIG).build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(STUDENT_NAME_LENGTH_INVALID.getCode(), errors.getAllErrors().get(0).getCode());
    }
}
