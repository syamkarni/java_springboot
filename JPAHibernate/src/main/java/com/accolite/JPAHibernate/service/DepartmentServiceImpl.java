package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.dto.DepartmentDTO;
import com.accolite.JPAHibernate.entity.Department;
import com.accolite.JPAHibernate.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        
        Department savedDepartment = departmentRepository.save(department);
        return mapToDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        // Clear the students list to avoid orphaned records
        if (department.getStudents() != null) {
            department.getStudents().clear();
        }
        
        departmentRepository.delete(department);
    }

    private DepartmentDTO mapToDTO(Department department) {
        return new DepartmentDTO(
            department.getDepartmentId(),
            department.getDepartmentName()
        );
    }
}
