package com.example.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.modules.entity.SysUserEntity;

/**
 * 用户
 * @author CaoRui
 * @date 2019年8月3日
 */
public interface SysUserServicr extends IService<SysUserEntity>{

    /**
     * 查询用户列表
     * @param pageIndex
     * @param pageSize
     * @param userName
     * @return
     */
    public IPage<SysUserEntity> getSysUserList(int pageIndex, int pageSize, String userName);
}
