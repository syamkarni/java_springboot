package com.accolite.JPAHibernate.repository;

import com.accolite.JPAHibernate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
