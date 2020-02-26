package com.example.dao;

import com.example.pojo.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Repository @Query注解
 *
 */
public interface StudentRepositoryQueryAnnotation extends Repository<Student,Integer> {
    //@Query(value = "from t_student where name=?")
    //List<Student> queryByNameUseHQL(String name);
    @Query(value = "from Student where name=:name")
    public List<Student> queryByNameUseHQL(@Param("name") String name);

    @Query(value = "select * from t_student where name=?",nativeQuery = true)
    List<Student> queryByNameUseSql(String name);

    /**
     * 测试方法中不加事务就会报如下异常：
     * org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query
     * 解决方式：在测试方法上添加事务注解
     */
    @Query("update Student set name=:name where id=:id")
    @Modifying
    void updateStudentNameById(@Param("name") String name, @Param("id") Integer id);
}
