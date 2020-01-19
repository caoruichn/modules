package com.example.modules.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户
 * @author CaoRui
 * @date 2019年8月3日
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(description="用户实体")
public class SysUserEntity {

    /**
     * 主键
     */
//    @TableId(type=IdType.UUID)
    @TableId(type=IdType.AUTO)
    @ApiModelProperty("用户编号")
    private Integer id;
    /**
     * 登录名
     */
    @JsonAlias(value = {"LoginName","Loginname","loginname","login_name"})
    @ApiModelProperty("登录名")
    private String loginName;
    /**
     * 密码
     */
    //@JsonAlias和@JsonProperty
    @JsonProperty("password")
    @ApiModelProperty("密码")
    private String pass;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 性别 1男、2女
     */
    @ApiModelProperty("性别 1男、2女")
    private Integer sex;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;
    /**
     * 用户类型 1管理、2普通
     */
    @ApiModelProperty("用户类型 1管理、2普通")
    private Integer  userType;
    /**
     * 状态 1正常、2禁用
     */
    @ApiModelProperty("状态 1正常、2禁用")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**
     * token
     */
    private String token;
    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastLogin;
    
    @TableField(exist=false)
    private String uuid;
    
    @TableField(exist=false)
    private String captcha;
}
