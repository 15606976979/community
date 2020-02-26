package com.example.springboot;


import com.example.App;
import com.example.service.UsersService;
import com.example.service.impl.UsersServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class UsersServiceImplTest {
    @Autowired
    private UsersService usersService;

    @Test
    public void TestFindAll(){
        usersService.findAll();
        System.out.println(usersService.findAll());
    }
}
