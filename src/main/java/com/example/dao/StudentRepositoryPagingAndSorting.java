package com.example.dao;

import com.example.pojo.Student;
import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 CrudRepository接口，主要完成一些增删改查的操作。注意：CrudRepository接口继承了Repository接口。
 *
 */
public interface StudentRepositoryPagingAndSorting extends PagingAndSortingRepository<Student,Integer> {

}

