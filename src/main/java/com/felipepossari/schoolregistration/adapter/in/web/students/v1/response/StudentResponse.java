package com.felipepossari.schoolregistration.adapter.in.web.students.v1.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
}
