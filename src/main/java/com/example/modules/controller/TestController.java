package com.example.modules.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.modules.exception.RRException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/testapi", method = { RequestMethod.GET,
        RequestMethod.POST }, produces = "application/json;charset=UTF-8")
@Api(tags="测试")
public class TestController {

    @ApiOperation(value="测试接口", notes="success")
    @RequestMapping(value = "getTest")
    public String testController() {
        log.info("test success");
        return "test success";
    }
    
    @ApiOperation(value="测试接口", notes="err")
    @RequestMapping(value = "getErr")
    public String testErr() {
        throw new RRException("test err");
    }
}
