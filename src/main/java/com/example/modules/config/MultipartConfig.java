package com.example.modules.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件大小限制，超出此大小页面会抛出异常信息
        // factory.setMaxFileSize("20480KB");//KB,MB
        factory.setMaxRequestSize(DataSize.of(20, DataUnit.MEGABYTES));
        // 设置总上传数据总大小
        // factory.setMaxRequestSize("20MB");
        factory.setMaxRequestSize(DataSize.of(20, DataUnit.MEGABYTES));
        // 设置文件临时文件夹路径
        // factory.setLocation("E://test//");
        // 如果文件大于这个值，将以文件的形式存储，如果小于这个值文件将存储在内存中，默认为0
        // factory.setMaxRequestSize(0);
        return factory.createMultipartConfig();
    }

}
