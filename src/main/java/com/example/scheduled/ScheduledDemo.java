package com.example.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * scheduled定时任务
 *
 */
@Component
public class ScheduledDemo {
    /**
     * 定时任务方法
     * @Scheduled:设置定时任务
     * cron属性：cron表达式，定时任务触发时间的字符串表达形式
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    public void scheduledMethod(){
        System.out.println("****触发定时器*****"+new Date());
    }
}
