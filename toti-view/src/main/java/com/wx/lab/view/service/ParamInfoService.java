package com.wx.lab.view.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.lab.view.po.ParamInfo;

public interface ParamInfoService extends IService<ParamInfo> {
    ParamInfo selectById(String paramId);

    void insertParam(ParamInfo paramInfo);
}
