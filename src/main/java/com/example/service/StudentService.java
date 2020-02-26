package com.example.service;

import com.example.pojo.Student;
import com.example.pojo.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findStudentById(Integer id);

    Page<Student> findStudentByPage(Pageable pageable);

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(Integer id);
}
