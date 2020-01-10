package com.example.modules.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.modules.dao.CaptchaMapper;
import com.example.modules.entity.CaptchaEntity;
import com.example.modules.service.CaptchaService;
import com.example.modules.utils.CreateImageUtil;

/**
 *
 * @author CaoRui
 * @date 2019年10月10日
 */
@Service
//@Slf4j
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, CaptchaEntity> implements CaptchaService{
    
    @Value("${captcha.path}")
    private String captchaRootPath;
    @Value("${captcha.length}")
    private Integer captchaLength;
    
    @Override
    public CaptchaEntity getCaptchaByUUID(String uuid) {
        if(StringUtils.isNoneBlank(uuid)) {
            QueryWrapper<CaptchaEntity> queryWrapper = new QueryWrapper<CaptchaEntity>();
            queryWrapper.eq("uuid", uuid)
            .orderByDesc("create_time");
            CaptchaEntity captchaEntity = this.getOne(queryWrapper);
            return captchaEntity;
        }
        return null;
    }

    @Override
    public boolean removeInvalidCaptcha() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        
        QueryWrapper<CaptchaEntity> queryWrapper = new QueryWrapper<CaptchaEntity>();
        queryWrapper.lt("create_time", date);
        return this.remove(queryWrapper);
    }

    @Override
    public String getCaptcha(String uuid) {
        String captchaCode = CreateImageUtil.getCaptchaCode(captchaLength, null);
        String fileName = uuid +".png";
        String captchaPath = captchaRootPath + fileName;
        try {
            CreateImageUtil.createImage(captchaCode, new File(captchaPath), 150, 60, 15);
        } catch (Exception e) {
            log.error("创建文件异常：{}", e);
        }
        CaptchaEntity entity = new CaptchaEntity();
        entity.setCaptcha(captchaCode);
        entity.setUuid(uuid);
        entity.setCreateTime(new Date());
        this.save(entity);
        return null;
    }

    @Override
    public String getCaptchaImg(String fileName) {
        return captchaRootPath + File.separator + fileName;
    }

}
