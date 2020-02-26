package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/exception")
public class ExceptionController {

    /*
       SpringBoot处理异常方式一：自定义错误页面
     */
    @RequestMapping("/showErors")
    public String showErors(){
        String str = null;
        str.length(); //此处必会抛空指针异常
        return "findUserAll";
    }
    /*
       SpringBoot处理异常方式二：@ExceptionHandler
     */
    @RequestMapping("/showErors2")
    public String showErors2(){
        System.out.println(3/0);//此处必会抛算数异常
        return "findUserAll";
    }

    /*@ExceptionHandler(value = {java.lang.ArithmeticException.class})//数组格式，可添加多个异常
    public ModelAndView arithmeticExceptionHandle(Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",e.toString());
        mv.setViewName("exception");
        return mv;
    }*/
}
