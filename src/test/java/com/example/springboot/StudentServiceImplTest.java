package com.example.springboot;


import com.example.App;
import com.example.dao.UserRepository;
import com.example.pojo.Student;
import com.example.pojo.User;
import com.example.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

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
public class StudentServiceImplTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void TestFindStudentById(){
        System.out.println("第一次查询="+studentService.findStudentById(2));
        System.out.println("第二次查询="+studentService.findStudentById(2));
    }

    @Test
    public void TestFindAll(){
        System.out.println("第一次查询="+studentService.findAll());
        System.out.println("第二次查询="+studentService.findAll());
        Student student = new Student();
        student.setName("蔡");
        student.setAddress("福建");
        student.setAge(22);
        studentService.saveStudent(student);
        System.out.println("第三次查询="+studentService.findAll());
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestAddUser(){
        User user = new User();
        user.setName("test");
        user.setAccountId(String.valueOf(123));
        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());

        userRepository.save(user);
    }

    @Test
    public void TestFindByToken(){
        String token = "7c64f26a-2045-438f-a0e8-9a093e53c65c";
        User user = userRepository.findByToken(token);
        System.out.println(user);
    }

}
