package com.example.modules.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author CaoRui
 * @date 2019年7月31日
 */
@EnableTransactionManagement
@Configuration
@MapperScan({ "com.example.modules.dao" })
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        // paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));//开启 count 的 join 优化,只针对 left join !!!
        return paginationInterceptor;
    }
}
