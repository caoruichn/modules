package com.example.modules.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.modules.dao.SysParamMapper;
import com.example.modules.entity.SysParamEntity;
import com.example.modules.exception.RRException;
import com.example.modules.service.SysParamServicr;

/**
 *
 * @author CaoRui
 * @date 2019年8月3日
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParamEntity> implements SysParamServicr{

    @Override
    public IPage<SysParamEntity> getSysParamList(int pageIndex, int pageSize, String pKey) {
        Page<SysParamEntity> page = new Page<SysParamEntity>(pageIndex, pageSize);
        QueryWrapper<SysParamEntity> queryWrapper = new QueryWrapper<SysParamEntity>();
        queryWrapper
            .like(StringUtils.isNotBlank(pKey), "p_key", pKey)
            .orderByDesc("create_time");
        IPage<SysParamEntity> pageInfo = this.page(page, queryWrapper);
        return pageInfo;
    }

    @Override
    public SysParamEntity getSysParamInfoByName(String pKey) {
        if(StringUtils.isBlank(pKey)) {
            throw new RRException("查询参数名称不能为空");
        }
        return this.getOne(new QueryWrapper<SysParamEntity>().eq("p_key", pKey));
    }

}
