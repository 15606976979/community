package com.example.service;

import com.example.pojo.Users;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface UsersService {
    void addUser(Users users);

    List<Users> findAll();

    Users findUserById(Integer id);

    void updateUser(Users user);

    void deleteUserById(Integer id);
}
