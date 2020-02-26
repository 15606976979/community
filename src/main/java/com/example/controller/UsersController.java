package com.example.controller;

import com.example.pojo.Users;
import com.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page,@ModelAttribute("user")Users users) {
        return page;
    }

    @RequestMapping("/addUser")
    public String addUser(@ModelAttribute("user") @Valid Users users , BindingResult result) {
        if (result.hasErrors()){
            System.out.println("result");
            return "input";
        }
        usersService.addUser(users);
        return "ok";
    }

    @RequestMapping("/findUserAll")
    public String findUserAll(Model model) {

        List<Users> list = usersService.findAll();
        model.addAttribute("list",list);
        return "showUsers";
    }

    @RequestMapping("/findUserById")
    public String findUserById(@ModelAttribute("users") Model model, Integer id) {
        Users user = usersService.findUserById(id);
        model.addAttribute("user",user);
        return "updateUser";
    }
    @RequestMapping("/editUser")
    public String editUser(Users users) {
        usersService.updateUser(users);
        return "ok";

    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Integer id) {
        usersService.deleteUserById(id);
        return "redirect:/users/findUserAll";
    }

}
