package com.example.modules.scheduling;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 自定义注解测试
 * @author CaoRui
 * @date 2019年10月12日
 */
@Component
@Aspect
public class AspectImpl {

    @Pointcut("@annotation(com.example.modules.scheduling.TestZhujJie)")
    public void annotationPointCut() {

    }
    
    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){
        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        TestZhujJie annotation = method.getAnnotation(TestZhujJie.class);
        System.out.println("打印："+annotation.value()+" 开始前");
        System.out.println("打印："+annotation.id()+" 开始前");
        System.out.println("打印："+annotation.name()+" 开始前");
    }
    
    @Around("annotationPointCut()")
    public Object advice(ProceedingJoinPoint joinPoint){
        System.out.println("通知之开始");
        Object retmsg=null;
        try {
             retmsg=joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("通知之结束");
        return retmsg;
    }
    
    @After("annotationPointCut()")
    public void after() {
        System.out.println("after方法执行后");
    }
}
