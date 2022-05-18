package com.felipepossari.schoolregistration.adapter.in.out.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class CourseEntity {

    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<StudentEntity> students;
}
