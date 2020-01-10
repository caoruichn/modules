package com.example.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码
 * @author CaoRui
 * @date 2019年10月10日
 */
@Getter
@Setter
@TableName("captcha_info")
public class CaptchaEntity {

    /**
     * 主键
     */
    @TableId(type=IdType.AUTO)
    private Integer id;
    
    private String uuid;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 创建时间
     */
    private Date createTime;
}
