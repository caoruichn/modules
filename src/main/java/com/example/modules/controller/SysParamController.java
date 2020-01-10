package com.example.modules.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.modules.entity.SysParamEntity;
import com.example.modules.service.SysParamServicr;
import com.example.modules.utils.R;

/**
 *
 * @author CaoRui
 * @date 2019年8月3日
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/sysparam", method = { RequestMethod.GET,
        RequestMethod.POST }, produces = "application/json;charset=UTF-8")
public class SysParamController {

    @Autowired
    SysParamServicr sysParamServicr;
    
    @RequestMapping(value = "/getlist")
    @ResponseBody
    public R getSysParamList(@RequestParam(required = false)Integer pageIndex,@RequestParam(required = false)Integer pageSize,@RequestParam(required = false)String pKey) {
        if(pageIndex == null) {
            pageIndex = 1;
        }
        if(pageSize == null) {
            pageSize = 10;
        }
        return R.ok().put("sysParamList", sysParamServicr.getSysParamList(pageIndex, pageSize, pKey));
    }
    
    @RequestMapping(value = "/getsysparam")
    @ResponseBody
    public R getSysParamById(Integer id) {
        if(id == null) {
            return R.error("查询失败，查询参数为空！");
        }
        return R.ok().put("sysparam", sysParamServicr.getById(id));
    }
    
    @RequestMapping(value = "/save")
    @ResponseBody
    public R saveSysParam(SysParamEntity sysParamEntity) {
        if(sysParamEntity == null) {
            return R.error("添加失败，添加数据为空！");
        }
        int count = sysParamServicr.count(new QueryWrapper<SysParamEntity>().eq("p_key", sysParamEntity.getPKey()));
        if(count > 0) {
            return R.error("添加失败，参数已存在！");
        }
        Date date = new Date();
        //sysParamEntity.setCreateUser(createUser);
        sysParamEntity.setCreateTime(date);
        sysParamEntity.setUpdateTime(date);
        if(!sysParamServicr.save(sysParamEntity)) {
            return R.error("添加失败，请重试！");
        }
        return R.ok();
    }
    
    @RequestMapping(value = "/update")
    @ResponseBody
    public R updateSysParamById(SysParamEntity sysParamEntity) {
        if(sysParamEntity == null) {
            return R.error("修改失败，数据为空！");
        }
        sysParamEntity.setUpdateTime(new Date());
        if(!sysParamServicr.updateById(sysParamEntity)) {
            return R.error("修改失败，请重试！");
        }
        return R.ok();
    }
    
    @RequestMapping(value = "/delete")
    @ResponseBody
    public R deleteSysParamById(Integer id) {
        if(id == null) {
            return R.error("删除失败，参数为空！");
        }
        if(!sysParamServicr.removeById(id)) {
            return R.error("删除失败，请重试！");
        }
        return R.ok();
    }
}
