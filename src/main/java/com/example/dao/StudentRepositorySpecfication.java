package com.example.dao;

import com.example.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 该接口主要是提供了多条件查询的支持，并且可以在查询中添加分页与排序。注意：JPASpecificationExecutor是单独存在的，完全独立。
 经常JpaRepository与JPASpecificationExecutor一起使用。
 *
 */
public interface StudentRepositorySpecfication extends JpaSpecificationExecutor<Student>, JpaRepository<Student, Integer> {

}

