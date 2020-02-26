package com.example.dao;

import com.example.pojo.Student;
import com.example.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 该接口主要是提供了多条件查询的支持，并且可以在查询中添加分页与排序。注意：JPASpecificationExecutor是单独存在的，完全独立。
 经常JpaRepository与JPASpecificationExecutor一起使用。
 *
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {
    User findByToken(String token);
}

