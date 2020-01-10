package com.example.modules.scheduling;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.modules.service.CaptchaService;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 * @author CaoRui
 * @date 2019年9月30日
 */
@Slf4j
@Component
public class AccessTokenThread {
    
    @Autowired
    private CaptchaService captchaService;
    
    @Async
    @Scheduled(fixedDelay = 1000*60) //每天执行一次
    public void testScheduled() {
        log.info("Testing");
    }
    
    /**
     * 定时（一天）删除过期验证码
     */
    @Async
    @Scheduled(fixedDelay = 1000*60*60*24) //每天执行一次
    public void removeCaptcha() {
        log.info("删除过期验证码时间-{}", new Date());
        log.info("删除过期验证码线程名-{}", Thread.currentThread().getName());
        boolean result = captchaService.removeInvalidCaptcha();
        log.info("删除过期验证码结果-{}", result);
    }
}
