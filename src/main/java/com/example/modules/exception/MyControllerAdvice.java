package com.example.modules.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author CaoRui
 * @date 2019年9月16日
 */
@ControllerAdvice  //不指定包默认加了@Controller和@RestController都能控制
public class MyControllerAdvice {

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param myex
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public RRException exceptionHandler(Exception ex){
//        RRException myex = new RRException(ex.getMessage(), -1);
//        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
//        return myex;
//    }
    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param myex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RRException.class)
    public RRException myExceptionHandler(RRException myex){
        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
        return myex;
    }
}
