package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.dto.StudentDTO;
import com.accolite.JPAHibernate.entity.Student;

import java.util.List;

public interface StudentService {
    public StudentDTO saveStudent(StudentDTO dto);
    public List<StudentDTO> getAllStudents();
    public StudentDTO getStudentById(Long id);
    public Student updateStudent(Long id, StudentDTO dto);
    public void deleteStudent(Long id);
}
