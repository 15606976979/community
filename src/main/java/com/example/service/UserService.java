package com.example.service;

import com.example.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserById(Integer id);

    User findUserByToken(String token);

    Page<User> findUserByPage(Pageable pageable);

    void saveUser(User User);

    void updateUser(User User);

    void deleteUserById(Integer id);
}
