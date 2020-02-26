package com.example.springboot;


import com.example.App;
import com.example.dao.AdminRepository;
import com.example.dao.RoleRepository;
import com.example.pojo.Admin;
import com.example.pojo.Menu;
import com.example.pojo.Role;
import com.example.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.Set;

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
public class MappingTest {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;
    /**
     * 一对多关联关系的添加
     */
    @Test
    public void testOneToManySave(){
        // 创建一个用户
        Admin admin = new Admin();
        admin.setName("蔡");
        admin.setAge(18);
        admin.setAddress("厦门");
        // 创建一个角色
        Role role = new Role();
        role.setRoleName("管理员");

        // 关联
        role.getAdmins().add(admin);
        admin.setRole(role);
        // 保存 需要user中role添加cascade=CascadeType.PERSIST
        adminRepository.save(admin);
    }

    /**
     * 一对多关联关系的查询
     */
    @Test
    public void testOneToManyFind(){
        Optional<Admin> optionalAdmin = adminRepository.findById(2);
        Admin admin = optionalAdmin.get();
        System.out.println(admin.toString());
        Role role = admin.getRole();
        System.out.println(role.toString());
    }

    @Test
    public void testManyToManySave(){
        // 创建角色对象
        Role role = new Role();
        role.setRoleName("项目经理");
        // 创建菜单对象
        Menu menu1 = new Menu();
        menu1.setMenuName("用户管理");
        menu1.setFatherId(0);
        menu1.setMenuUrl("www.111.com");

        Menu menu2 = new Menu();
        menu2.setMenuName("角色管理");
        menu2.setFatherId(1);
        menu2.setMenuUrl("www.222.com");
        // 关联
        role.getMenus().add(menu1);
        role.getMenus().add(menu2);
        menu1.getRoles().add(role);
        menu2.getRoles().add(role);
        // 保存
        this.roleRepository.save(role);
    }
    /**
     * org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
     * com.bjsxt.pojo.Roles.menus, could not initialize proxy - no Session
     * 原因是没加在roles对象的menus字段中加fetch=FetchType.EAGER
     */
    @Test
    public void testManyToManyFind(){
        Optional<Role> optionalRole = roleRepository.findById(3);
        Role role = optionalRole.get();
        System.out.println(role.toString());
        Set<Menu> menus = role.getMenus();
        for (Menu menu:menus){
            System.out.println(menu.toString());
        }
    }
}
