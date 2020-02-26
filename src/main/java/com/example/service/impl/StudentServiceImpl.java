package com.example.service.impl;

import com.example.dao.StudentRepository;
import com.example.mapper.UsersMapper;
import com.example.pojo.Student;
import com.example.pojo.Users;
import com.example.service.StudentService;
import com.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired(required = false)
    private StudentRepository studentRepository;


    @Override
    @Cacheable(value = "student")
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Cacheable(value = "student")
    public Student findStudentById(Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.get();
    }

    @Override
    public Page<Student> findStudentByPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "student",allEntries = true)
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }
}
