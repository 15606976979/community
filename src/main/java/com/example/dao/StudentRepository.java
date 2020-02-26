package com.example.dao;

import com.example.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 JpaRepository<T, ID>
 * 参数一T：当前需要映射的实体
 * 参数二ID：当前映射的实体中OID的类型
 *
 */
public interface StudentRepository extends JpaSpecificationExecutor<Student>,JpaRepository<Student,Integer> {

}

