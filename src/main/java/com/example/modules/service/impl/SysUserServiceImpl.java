package com.example.modules.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.modules.dao.SysUserMapper;
import com.example.modules.entity.SysUserEntity;
import com.example.modules.service.SysUserServicr;

/**
*
* @author CaoRui
* @date 2019年8月3日
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserServicr{

    @Override
    public IPage<SysUserEntity> getSysUserList(int pageIndex, int pageSize, String userName) {
        Page<SysUserEntity> page = new Page<SysUserEntity>(pageIndex, pageSize);
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
        queryWrapper
            //.select(SysUserEntity.class,i -> !i.getColumn().equals("psaa") && !i.getColumn().equals("token"))
            .select("create_time,email,id,last_login,login_name,name,phone,sex,status,update_time,user_type")
            .like(StringUtils.isNotBlank(userName), "name", userName)
            .ne("login_name", "admin")
            .orderByDesc("create_time");
        IPage<SysUserEntity> pageInfo = this.page(page, queryWrapper);
        return pageInfo;
    }

}
