package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.dto.AddressDTO;
import com.accolite.JPAHibernate.dto.CourseDTO;
import com.accolite.JPAHibernate.dto.DepartmentDTO;
import com.accolite.JPAHibernate.dto.ProductDTO;
import com.accolite.JPAHibernate.dto.StudentDTO;
import com.accolite.JPAHibernate.entity.Address;
import com.accolite.JPAHibernate.entity.Cource;
import com.accolite.JPAHibernate.entity.Department;
import com.accolite.JPAHibernate.entity.Product;
import com.accolite.JPAHibernate.entity.Student;
import com.accolite.JPAHibernate.repository.CourceRepository;
import com.accolite.JPAHibernate.repository.DepartmentRepository;
import com.accolite.JPAHibernate.repository.ProductRepository;
import com.accolite.JPAHibernate.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourceRepository courceRepository;

    @Autowired
    private ProductRepository productRepository;

    private Cource mapToCourse(CourseDTO courseDTO) {
        Cource course = new Cource();
        course.setCourseId(courseDTO.getCourseId());
        course.setTitle(courseDTO.getTitle());
        course.setDuration(courseDTO.getDuration());
        return course;
    }

    private CourseDTO mapToCourseDTO(Cource course) {
        return new CourseDTO(
            course.getCourseId(),
            course.getTitle(),
            course.getDuration()
        );
    }

    private Product mapToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    private ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
            product.getProductId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity()
        );
    }

    @Override
    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        try {
            Student student = new Student();
            student.setName(studentDTO.getStudentName());
            
            // Handle address
            if (studentDTO.getAddress() != null) {
                Address address = new Address();
                address.setStreet(studentDTO.getAddress().getStreet());
                address.setCity(studentDTO.getAddress().getCity());
                address.setZipcode(studentDTO.getAddress().getZipcode());
                student.setAddress(address);
            }

            // Handle department
            if (studentDTO.getDepartment() != null) {
                Department department = new Department();
                department.setDepartmentName(studentDTO.getDepartment().getDepartmentName());
                student.setDepartment(department);
            }

            // Handle courses
            if (studentDTO.getCourses() != null && !studentDTO.getCourses().isEmpty()) {
                Set<Cource> courses = studentDTO.getCourses().stream()
                    .map(this::mapToCourse)
                    .collect(Collectors.toSet());
                student.setCourses(courses);
            }

            // Handle products
            if (studentDTO.getProducts() != null && !studentDTO.getProducts().isEmpty()) {
                Set<Product> products = studentDTO.getProducts().stream()
                    .map(this::mapToProduct)
                    .collect(Collectors.toSet());
                student.setProducts(products);
            }

            Student savedStudent = studentRepository.saveAndFlush(student);
            return mapToDTO(savedStudent);
        } catch (Exception e) {
            throw new RuntimeException("Error saving student: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        existingStudent.setName(studentDTO.getStudentName());

        if (studentDTO.getAddress() != null) {
            Address address = existingStudent.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setStreet(studentDTO.getAddress().getStreet());
            address.setCity(studentDTO.getAddress().getCity());
            address.setZipcode(studentDTO.getAddress().getZipcode());
            existingStudent.setAddress(address);
        }

        if (studentDTO.getDepartment() != null) {
            Department department = new Department();
            department.setDepartmentName(studentDTO.getDepartment().getDepartmentName());
            Department savedDepartment = departmentRepository.save(department);
            existingStudent.setDepartment(savedDepartment);
        }

        // Handle courses
        if (studentDTO.getCourses() != null) {
            Set<Cource> courses = studentDTO.getCourses().stream()
                .map(this::mapToCourse)
                .collect(Collectors.toSet());
            existingStudent.setCourses(courses);
        }

        // Handle products
        if (studentDTO.getProducts() != null) {
            Set<Product> products = studentDTO.getProducts().stream()
                .map(this::mapToProduct)
                .collect(Collectors.toSet());
            existingStudent.setProducts(products);
        }

        return studentRepository.save(existingStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Clear the courses set to avoid orphaned records
        if (student.getCourses() != null) {
            student.getCourses().clear();
        }
        
        // Clear the products set to avoid orphaned records
        if (student.getProducts() != null) {
            student.getProducts().clear();
        }
        
        // Delete the student
        studentRepository.delete(student);
    }

    private StudentDTO mapToDTO(Student student) {
        AddressDTO addressDTO = null;
        if (student.getAddress() != null) {
            addressDTO = new AddressDTO(
                student.getAddress().getAddressId(),
                student.getAddress().getStreet(),
                student.getAddress().getCity(),
                student.getAddress().getZipcode()
            );
        }

        DepartmentDTO departmentDTO = null;
        if (student.getDepartment() != null) {
            departmentDTO = new DepartmentDTO(
                student.getDepartment().getDepartmentId(),
                student.getDepartment().getDepartmentName()
            );
        }

        Set<CourseDTO> courseDTOs = null;
        if (student.getCourses() != null) {
            courseDTOs = student.getCourses().stream()
                .map(this::mapToCourseDTO)
                .collect(Collectors.toSet());
        }

        Set<ProductDTO> productDTOs = null;
        if (student.getProducts() != null) {
            productDTOs = student.getProducts().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toSet());
        }
        
        return new StudentDTO(
            student.getStudentId(),
            student.getName(),
            addressDTO,
            departmentDTO,
            courseDTOs,
            productDTOs
        );
    }
}
