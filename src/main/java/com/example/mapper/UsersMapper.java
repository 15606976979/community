package com.example.mapper;

import com.example.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Mapper
public interface UsersMapper {
    // 方法名对应映射配置文件里面的id值
    void insertUser(Users user);

    //用户查询
    List<Users> selectAll();

    //通过id查找对象
    Users findUserById(Integer id);

    //修改用户
    void updateUser(Users user);

    void deleteUserById(Integer id);
}
