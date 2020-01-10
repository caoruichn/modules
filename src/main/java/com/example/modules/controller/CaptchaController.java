package com.example.modules.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modules.exception.RRException;
import com.example.modules.service.CaptchaService;
import com.example.modules.utils.R;

/**
 *
 * @author CaoRui
 * @date 2019年10月10日
 */
@RestController
@RequestMapping(value = "/captcha", method = { RequestMethod.GET,
        RequestMethod.POST }, produces = "application/json;charset=UTF-8")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;
    
    @ResponseBody
    @RequestMapping("/getcaptcha")
    public R getCaptcha(String uuid){
        if(StringUtils.isBlank(uuid)) {
            throw new RRException("参数不能为空");
        }
        String fileName = captchaService.getCaptcha(uuid);
        return R.ok().put("captchaPath", fileName);
    }
    
    @ResponseBody
    @RequestMapping("/getcaptchaimg")
    public R getCaptchaImg(String captchaUrl, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        // 禁止图像缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        File file = new File(captchaService.getCaptchaImg(captchaUrl));
        FileInputStream inputStream = new FileInputStream(file);
        if (inputStream != null){
            // 得到文件大小
            int i = inputStream.available();
            byte[] data = new byte[i];
            // 读数据
            inputStream.read(data);
            inputStream.close();
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.close();
        }
        return null;
    }
}
