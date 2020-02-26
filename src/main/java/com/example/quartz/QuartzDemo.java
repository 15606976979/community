package com.example.quartz;


import com.example.service.StudentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class QuartzDemo implements Job {
    @Autowired
    private StudentService studentService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Execute......" + new Date());
        System.out.println("Execute......" + studentService.findAll());
    }
}
