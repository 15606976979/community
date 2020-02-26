package com.example.dao;

import com.example.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 CrudRepository接口，主要完成一些增删改查的操作。注意：CrudRepository接口继承了Repository接口。
 *
 */
public interface StudentRepositoryCrudRepository extends CrudRepository<Student,Integer> {

}

