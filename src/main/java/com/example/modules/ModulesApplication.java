package com.example.modules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.spring4all.swagger.EnableSwagger2Doc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableSwagger2Doc
@EnableScheduling // 定时任务
@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ModulesApplication {

    public static void main(String[] args) {
        boolean byOs = false;
        if(byOs) {
            log.info("根据当前OS判断配置文件");
            runProByOs(args);
        } else {
            log.info("直接启动");
            runPro(args);
        }
    }
    
    public static void runPro(String[] args) {
        log.info("启动中...");
        SpringApplication.run(ModulesApplication.class, args);
        log.info("启动完成");
    }
    
    public static void runProByOs(String[] args) {
        log.info("启动中...");
        SpringApplication app = new SpringApplication(ModulesApplication.class);
        String os = System.getProperty("os.name");
        log.info("当前系统为：{}", os);
        if (os.toLowerCase().indexOf("windows") >= 0) {
            // 使开发环境的配置文件生效
            log.info("加载配置文件：{}", "dev");
            app.setAdditionalProfiles("dev");
        } else {
            // 使正式环境的配置文件生效
            log.info("加载配置文件：{}", "prod");
            app.setAdditionalProfiles("prod");
        }
        app.run(args);
        // ConfigurableApplicationContext context = app.run(args);
        // System.out.println("=====部分配置===========================================");
        // System.out.println("server.port = " +
        // context.getEnvironment().getProperty("server.port"));
        // System.out.println("server.servlet.context-path = " +
        // context.getEnvironment().getProperty("server.servlet.context-path"));
        // System.out.println("captcha.path = " +
        // context.getEnvironment().getProperty("captcha.path"));
        // System.out.println("logging.file = " +
        // context.getEnvironment().getProperty("logging.file"));
        // System.out.println("=====显示结束===========================================");
        log.info("启动完成");
        // 运行结束进行关闭操作
        // context.close();
    }

}
