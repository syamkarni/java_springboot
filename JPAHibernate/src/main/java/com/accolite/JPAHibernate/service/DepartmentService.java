package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.dto.DepartmentDTO;
import com.accolite.JPAHibernate.entity.Department;

import java.util.List;

public interface DepartmentService {
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    void deleteDepartment(Long id);
}
