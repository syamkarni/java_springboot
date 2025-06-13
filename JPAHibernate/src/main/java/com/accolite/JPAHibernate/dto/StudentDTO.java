package com.accolite.JPAHibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private AddressDTO address;
    private DepartmentDTO department;
    private Set<CourseDTO> courses;
    private Set<ProductDTO> products;
}
