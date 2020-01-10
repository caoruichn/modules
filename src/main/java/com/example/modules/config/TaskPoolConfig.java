package com.example.modules.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 多线程配置
 * @author CaoRui
 * @date 2019年10月12日
 */
@Configuration
@EnableAsync//开启异步
public class TaskPoolConfig {

    // 声明一个线程池(并指定线程池的名字)
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);//核心线程数
        executor.setMaxPoolSize(20);//最大线程数
        executor.setQueueCapacity(100);//缓冲执行任务的队列最大数
        executor.setKeepAliveSeconds(60);//超过了核心线程数之外的线程空闲后的最大存活时间
        executor.setThreadNamePrefix("cr-taskExecutor-");//线程名称前缀，方便定位处理任务所在的线程池
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
