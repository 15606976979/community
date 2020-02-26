package com.example.service.impl;

import com.example.mapper.UsersMapper;
import com.example.pojo.Users;
import com.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired(required = false)
    private UsersMapper usersMapper;

    @Override
    public void addUser(Users users) {
        usersMapper.insertUser(users);
    }

    @Override
    public List<Users> findAll() {
        return usersMapper.selectAll();
    }

    @Override
    public Users findUserById(Integer id) {
        return usersMapper.findUserById(id);
    }

    @Override
    public void updateUser(Users user) {
        usersMapper.updateUser(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        usersMapper.deleteUserById(id);
    }

}
