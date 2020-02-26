package com.example.dao;

import com.example.pojo.Student;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository接口的方法名称命名查询
 *
 */
public interface StudentRepositoryByName extends Repository<Student,Integer> {
    // 方法的名称必须要遵循驼峰式命名规则，
    // findBy(关键字)+属性名称（首字母要大写）+ 查询条件（首字母大写），如findByNameEquals，做相等查询的时候可以不写
    List<Student> findByName(String name);
    List<Student> findByNameAndAge(String name,Integer Age);
    List<Student> findByNameLike(String name);
}
