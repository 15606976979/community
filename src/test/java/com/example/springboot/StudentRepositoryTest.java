package com.example.springboot;


import com.example.App;
import com.example.dao.*;
import com.example.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * springboot测试类
 * @RunWith:启动类
 * SpringJUnit4ClassRunner.class 让junit与spring环境整合
 * @SpringBootTest(classes = {App.class})
 * 	1、当前类为springboot的测试类
 *  2、加载springboot启动类，启动springboot
 *
 * junit与spring整合@Contextconfiguration("classpath:applicationContext.xml")
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void addStudent(){
        Student student = new Student();
        student.setName("kankan");
        student.setAge(18);
        student.setAddress("厦门");
        studentRepository.save(student);
    }
    @Autowired
    private StudentRepositoryByName studentRepositoryByName;

    @Test
    public void testFindByName(){
        List<Student> lis = studentRepositoryByName.findByName("张三");
        for (Student student:lis){
            System.out.println(student);
        }
    }

    @Test
    public void testFindByNameAndAge(){
        List<Student> lis = studentRepositoryByName.findByNameAndAge("张三",18);
        for (Student student:lis){
            System.out.println(student);
        }
    }

    @Test
    public void testFindByNameLike(){
        List<Student> lis = studentRepositoryByName.findByNameLike("张%");
        for (Student student:lis){
            System.out.println(student);
        }
    }

    @Autowired
    private StudentRepositoryQueryAnnotation studentRepositoryQueryAnnotation;
    @Test
    public void testQueryByNameUseHQL(){
        List<Student> lis = studentRepositoryQueryAnnotation.queryByNameUseHQL("张三");
        for (Student student:lis){
            System.out.println(student);
        }
    }

    @Test
    public void testQueryByNameUseSQL() {
        List<Student> list = studentRepositoryQueryAnnotation.queryByNameUseSql("张三");
        for (Student student : list) {
            System.out.println(student);
        }
    }

    @Test
    @Transactional // 此注解一定要加，不加会更新异常，旧版本@Transactional和@Test一起使用时事务会自动回滚，所以需要添加@Rollback(false)来取消自动回滚
    @Rollback(false) // 取消自动回滚，新版本可不加此注释
    public void testUpdateUsersNameById() {
        studentRepositoryQueryAnnotation.updateStudentNameById("张三三", 2);
    }

    @Autowired
    private StudentRepositoryCrudRepository studentRepositoryCrudRepository;

    @Test
    public void testCrudSave() {
        Student student = new Student();
        student.setName("蔡");
        student.setAge(18);
        student.setAddress("厦门");
        this.studentRepositoryCrudRepository.save(student);
    }

    @Test
    public void testCrudSaveOrUpdate() {
        Student student = new Student();
        student.setId(3);
        student.setName("徐");
        student.setAge(18);
        student.setAddress("厦门");
        this.studentRepositoryCrudRepository.save(student);
    }

    @Test
    public void testCrudFindById() {
        Optional<Student> optional = this.studentRepositoryCrudRepository.findById(3);
        Student student = optional.get();
        System.out.println(student);
    }

    @Test
    public void testCrudFindAll() {
        Iterable<Student> students = this.studentRepositoryCrudRepository.findAll();
        for (Student student:students) {
            System.out.println(student);
        }
    }
    @Test
    public void testCrudDelete() {
        this.studentRepositoryCrudRepository.deleteById(3);
    }

    @Autowired
    private StudentRepositoryPagingAndSorting studentRepositoryPagingAndSorting;

    @Test
    public void testFindAllPagingAndSorting(){
        //Direction是用来标识列属性升序还是降序排序的,properties即为列属性
        // Pageable封装了分页的参数，当前页，每页显示的条数，注意：他的当前页是从0开始的
        // page:当前页；size:每页显示的条数
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(0,2,sort);
        Page<Student> page = studentRepositoryPagingAndSorting.findAll(pageable);
        System.out.println("总条数" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        List<Student> lis = page.getContent();
        for(Student student:lis){
            System.out.println(student);
        }

    }

    @Autowired
    private StudentRepositorySpecfication studentRepositorySpecfication;

    //单个查询条件
    @Test
    public void testFindAllSpecifications1(){
        Specification<Student> specifi = new Specification<Student>() {
            // Predicate：封装了单个查询条件
            /**
             * Root<Users> root：查询对象的属性的封装
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，如select from order by等
             * CriteriaBuilder criteriaBuilder：查询条件的构造器
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // where name = 'guozi11'
                /**
                 * 参数1：查询的条件属性
                 * 参数2：条件的值
                 */
                Predicate predicate = criteriaBuilder.equal(root.get("name"), "徐");
                return predicate;
            }
        };

        List<Student> lis = studentRepositorySpecfication.findAll(specifi);
        for(Student student:lis){
            System.out.println(student);
        }
    }

    //多个and查询条件
    @Test
    public void testFindAllSpecifications2(){
        Specification<Student> specifi = new Specification<Student>() {
            // Predicate：封装了单个查询条件
            /**
             * Root<Users> root：查询对象的属性的封装
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，如select from order by等
             * CriteriaBuilder criteriaBuilder：查询条件的构造器
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // where name = 'guozi11'
                /**
                 * 参数1：查询的条件属性
                 * 参数2：条件的值
                 */
                List<Predicate> listPre = new ArrayList<>();
                listPre.add(criteriaBuilder.equal(root.get("name"), "徐"));
                listPre.add(criteriaBuilder.equal(root.get("age"), "18"));
                Predicate[] arr = new Predicate[listPre.size()];
                Predicate predicate = criteriaBuilder.and(listPre.toArray(arr));
                return predicate;
            }
        };

        List<Student> lis = studentRepositorySpecfication.findAll(specifi);
        for(Student student:lis){
            System.out.println(student);
        }
    }

    //多个and/or查询条件
    @Test
    public void testFindAllSpecifications3(){
        Specification<Student> specifi = new Specification<Student>() {
            // Predicate：封装了单个查询条件
            /**
             * Root<Users> root：查询对象的属性的封装
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，如select from order by等
             * CriteriaBuilder criteriaBuilder：查询条件的构造器
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // where name = 'guozi11'
                /**
                 * 参数1：查询的条件属性
                 * 参数2：条件的值
                 */
                List<Predicate> listPre = new ArrayList<>();
                listPre.add(criteriaBuilder.equal(root.get("name"), "徐"));
                listPre.add(criteriaBuilder.equal(root.get("age"), "18"));
                Predicate[] arr = new Predicate[listPre.size()];
                Predicate predicate = criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), "徐"),criteriaBuilder.equal(root.get("age"), "18")),criteriaBuilder.equal(root.get("id"), 1));
                return predicate;
            }
        };

        List<Student> lis = studentRepositorySpecfication.findAll(specifi);
        for(Student student:lis){
            System.out.println(student);
        }
    }

    //多条件测试:方式二+排序
    @Test
    public void testFindAllSpecifications4(){
        Specification<Student> specifi = new Specification<Student>() {
            // Predicate：封装了单个查询条件
            /**
             * Root<Users> root：查询对象的属性的封装
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，如select from order by等
             * CriteriaBuilder criteriaBuilder：查询条件的构造器
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // where name = 'guozi11'
                /**
                 * 参数1：查询的条件属性
                 * 参数2：条件的值
                 */
                List<Predicate> listPre = new ArrayList<>();
                listPre.add(criteriaBuilder.equal(root.get("name"), "徐"));
                listPre.add(criteriaBuilder.equal(root.get("age"), "18"));
                Predicate[] arr = new Predicate[listPre.size()];
                Predicate predicate = criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), "徐"),criteriaBuilder.equal(root.get("age"), "18")),criteriaBuilder.equal(root.get("id"), 1));
                return predicate;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(0,2,sort);
        List<Student> lis = studentRepositorySpecfication.findAll(specifi,sort);
        for(Student student:lis){
            System.out.println(student);
        }
    }


}
