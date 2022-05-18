package com.felipepossari.schoolregistration.application.port.in;

public interface StudentEnrollmentUseCase {

    void enroll(Long studentId, Long courseId);

    void cancelEnrollment(Long studentId, Long courseId);
}
