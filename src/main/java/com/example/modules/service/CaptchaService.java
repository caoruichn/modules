package com.example.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.modules.entity.CaptchaEntity;

/**
 *
 * @author CaoRui
 * @date 2019年10月10日
 */
public interface CaptchaService extends IService<CaptchaEntity>{

    /**
     * 获取验证码
     * @Title: getCaptcha
     * @Description: 根据uuid生成验证码图片
     * @param fileName
     * @return 图片名称
     * @author CaoRUI
     * @date 2020-01-10 09:15:56
     */
    public String getCaptcha(String uuid);
    /**
     * 获取验证码图片路径
     * @Title: getCaptchaImg
     * @Description: 根据名称获取验证码图片路径
     * @param fileName 名称
     * @return 验证码图片路径
     * @author CaoRUI
     * @date 2020-01-10 09:19:10
     */
    public String getCaptchaImg(String fileName);
    /**
     * 根据uuid获取验证码信息
     * @param uuid
     * @return
     */
    public CaptchaEntity getCaptchaByUUID(String uuid);
    /**
     * 删除一天前的验证码
     * @return
     */
    public boolean removeInvalidCaptcha();
}
