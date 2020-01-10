package com.example.modules.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CaoRui
 * @date 2019年8月3日
 */
@Getter
@Setter
@TableName("sys_param")
public class SysParamEntity {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 键
     */
    private String pKey;
    /**
     * 值
     */
    private String pValue;
    /**
     * 创建/修改人
     */
    private String createUser;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**
     * 参数描述
     */
    private String pDescribe;
}
