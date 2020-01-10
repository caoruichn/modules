package com.example.modules.scheduling;

import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

/**
 * 自定义注解测试：@TestZhujJie(value="test",id=51,name="删除验证码")
 * @author CaoRui
 * @date 2019年10月12日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TestZhujJie {
    String value() default "";
    int id() default 0;
    String name() default "";
}
