package com.example.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.modules.entity.SysParamEntity;

/**
 * @ClassName: SysParamServicr
 * @Description: 系统参数
 * @author CaoRui
 * @date 2020-01-09 17:38:44
 */
public interface SysParamServicr extends IService<SysParamEntity>{

    /**
     * 查询系统参数
     * @Title: getSysParamList
     * @Description: 根据参数名（like）查询系统参数，含分页
     * @param pageIndex
     * @param pageSize
     * @param pKey 参数名关键字
     * @return 参数列表
     * @author CaoRUI
     * @date 2020-01-09 17:39:21
     */
    public IPage<SysParamEntity> getSysParamList(int pageIndex, int pageSize, String pKey);
    /**
     * 查询参数信息
     * @Title: getSysParamInfoByName
     * @Description: 根据参数名查询参数信息
     * @param paramName
     * @return 参数信息
     * @author CaoRUI
     * @date 2020-01-09 17:41:38
     */
    public SysParamEntity getSysParamInfoByName(String paramName);
}
