package com.example.modules.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.modules.entity.CaptchaEntity;
import com.example.modules.entity.SysUserEntity;
import com.example.modules.jwt.JavaWebToken;
import com.example.modules.service.CaptchaService;
import com.example.modules.service.SysUserServicr;
import com.example.modules.utils.MD5Util;
import com.example.modules.utils.R;
import com.example.modules.utils.RsaUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author CaoRui
 * @date 2019年8月3日
 */
@Api(tags="用户管理")
@RestController
@RequestMapping(value = "/sysuser", method = { RequestMethod.GET,
        RequestMethod.POST }, produces = "application/json;charset=UTF-8")
public class SysUserController {
    
    @Autowired
    private SysUserServicr sysUserServicr;
    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value="获取用户列表", notes="用于获取用户分页列表，分页参数及查询参数可选")
    @RequestMapping("/getlist")
    @ResponseBody
    public R getSysParamList(@RequestParam(required = false)Integer pageIndex,@RequestParam(required = false)Integer pageSize,@RequestParam(required = false)String userName) {
        if(pageIndex == null) {
            pageIndex = 1;
        }
        if(pageSize == null) {
            pageSize = 10;
        }
        return R.ok().put("sysParamList", sysUserServicr.getSysUserList(pageIndex, pageSize, userName));
    }
    
    @ApiOperation(value="获取用户信息", notes="用于根据用户id获取用户信息")
    @ApiImplicitParam(dataType = "Integer", name = "id", value = "用户编号")
    @RequestMapping(value = "/getsysuser")
    @ResponseBody
    public R getSysUserById(Integer id) {
        if(id == null) {
            return R.error("查询失败，查询参数为空！");
        }
        SysUserEntity userInfo = sysUserServicr.getById(id);
        if(userInfo != null) {
            userInfo.setPass(null);
            userInfo.setToken(null);
        }
        return R.ok().put("sysparam", userInfo);
    }
    /**
     * 保存，密码使用md5简单加密
     * @param sysUserEntity
     * @return
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(value = "/save")
    @ResponseBody
    public R saveSysUser(@RequestBody SysUserEntity sysUserEntity) {
        if(sysUserEntity == null) {
            return R.error("添加失败，添加数据为空！");
        }
        if(StringUtils.isBlank(sysUserEntity.getName())) {
            return R.error("添加失败，用户名不能为空！");
        }
        if(StringUtils.isBlank(sysUserEntity.getPass())) {
            return R.error("添加失败，密码不能为空！");
        }
        int count = sysUserServicr.count(new QueryWrapper<SysUserEntity>().eq("login_name", sysUserEntity.getLoginName()));
        if(count > 0) {
            return R.error("添加失败，登录名已存在！请登录或更换其他登录名！");
        }
        // MD5加密密码
        sysUserEntity.setPass(MD5Util.getMD5(sysUserEntity.getPass()));
        Date date = new Date();
        sysUserEntity.setCreateTime(date);
        sysUserEntity.setUpdateTime(date);
        if(!sysUserServicr.save(sysUserEntity)) {
            return R.error("添加失败，请重试！");
        }
        return R.ok();
    }
    /**
     * 根据id更新
     * @param sysUserEntity
     * @return
     */
    @ApiOperation(value="更新用户", notes="根据用户ID更新用户信息")
    @RequestMapping(value = "/update")
    @ResponseBody
    public R updateSysUserById(@RequestBody SysUserEntity sysUserEntity) {
        if(sysUserEntity == null) {
            return R.error("修改失败，数据为空！");
        }
        if(sysUserEntity.getId() ==null) {
            return R.error("修改失败，修改数据异常，请重试！");
        }
        // 更新用户不更新密码
        sysUserEntity.setPass(null);
        sysUserEntity.setUpdateTime(new Date());
        if(!sysUserServicr.updateById(sysUserEntity)) {
            return R.error("修改失败，请重试！");
        }
        return R.ok();
    }
    /**
     * 根据id删除
     */
    @ApiOperation(value="删除用户", notes="根据用户ID删除用户信息")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public R deleteSysUserById(@RequestBody SysUserEntity sysUserEntity) {
        Integer id = sysUserEntity.getId();
        if(id == null) {
            return R.error("删除失败，参数为空！");
        }
        if(sysUserServicr.getById(id) == null) {
            return R.error("删除失败，用户不存在！");
        }
        if(!sysUserServicr.removeById(id)) {
            return R.error("删除失败，请重试！");
        }
        return R.ok();
    }
    
    /**
     * 登录成功返回用户名、登录名、token
     * @param sysUserEntity
     * @return
     */
    @ApiOperation(value="登录", notes="使用用户名，密码（加密），验证码登录")
    @RequestMapping("/login")
    @ResponseBody
    public R loginSysUser(@RequestBody SysUserEntity sysUserEntity) {
        if(StringUtils.isBlank(sysUserEntity.getUuid()) || StringUtils.isBlank(sysUserEntity.getCaptcha())) {
            return R.error("登录失败，验证码不正确！");
        }
        if(StringUtils.isBlank(sysUserEntity.getLoginName()) || StringUtils.isBlank(sysUserEntity.getPass())) {
            return R.error("登录失败，登录名或密码为空！");
        }
        CaptchaEntity captchaEntity = captchaService.getCaptchaByUUID(sysUserEntity.getUuid());
        if(!sysUserEntity.getCaptcha().toUpperCase().equals(captchaEntity.getCaptcha())) {
            return R.error("登录失败，验证码不正确！");
        }
        // 根据登录名查询用户
        SysUserEntity userInfo = sysUserServicr.getOne(
                new QueryWrapper<SysUserEntity>()
                .eq("login_name", sysUserEntity.getLoginName()));
        if(userInfo == null) {
            return R.error("登录失败，用户不存在！");
        }
        String o_pass = RsaUtils.decryptByPrivateKey(sysUserEntity.getPass());
        // 判断密码是否正确
        if(!MD5Util.authenticatePassword(userInfo.getPass(), o_pass)) {
            return R.error("登录失败，登录名或密码错误！");
        }
        Map<String,Object> tokenMap = new HashMap <>();
        tokenMap.put("userId", userInfo.getId());
        String token = JavaWebToken.createJavaWebToken(tokenMap);
        // 更新用户登录时间、token
        SysUserEntity updateToken = new SysUserEntity();
        updateToken.setId(userInfo.getId());
        updateToken.setLastLogin(new Date());
        updateToken.setToken(token);
        if(!sysUserServicr.updateById(updateToken)) {
            return R.error("用户登录信息设置失败，请重试！");
        }
        updateToken.setLoginName(userInfo.getLoginName());
        updateToken.setName(userInfo.getName());
        return R.ok().put("userInfo", updateToken);
    }
}
