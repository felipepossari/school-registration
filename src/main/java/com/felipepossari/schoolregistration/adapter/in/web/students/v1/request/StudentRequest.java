package com.felipepossari.schoolregistration.adapter.in.web.students.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudentRequest {
    private String name;
    private String email;
}
