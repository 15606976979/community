package com.example.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        // 判断不同异常类型，做不同视图跳转
        if(e instanceof ArithmeticException) {
            mv.setViewName("exception");
        }
        if(e instanceof NullPointerException) {
            mv.setViewName("exception");
        }
        mv.addObject("error",e.toString());
        return mv;
    }
}
