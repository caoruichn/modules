package com.example.modules;

import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModulesApplication.class)
public class TestInfo {

    public static void main(String[] args) {
        //test1();
        test2();
        test3();
    }
    
    public static void test3() {
        System.out.println("".isBlank());
        System.out.println(" ".isBlank());
        System.out.println("\\s".isBlank()); 
        System.out.println("1".isBlank());
        System.out.println(" 1 ".isBlank());
        // 不能为null，否则NullPointerException
        //String str = null;
        //System.out.println(str.isBlank());
    }
    
    public static void test2() {
        var str = "123123\n456789\n789789";
        var num = 123;
        System.out.println(str + num);
        System.out.println("字符串行数：" + str.lines().count());
    }
    @Test
    public void test1() {
        String str = "123.456a789";
        String regex = ".*456.*";
        System.out.println(str.matches(regex));
        System.out.println(Pattern.matches(regex, str));
    }
}
