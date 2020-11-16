package com.wx.lab.view.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.lab.view.po.ParamInfo;
import com.wx.lab.view.mapper.ParamInfoMapper;
import com.wx.lab.view.service.ParamInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paramInfoService")
public class ParamInfoServiceImpl extends ServiceImpl<ParamInfoMapper, ParamInfo> implements ParamInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamInfoServiceImpl.class);

    @Autowired
    private ParamInfoMapper paramInfoMapper;

    @Override
    public ParamInfo selectById(String paramId) {
        ParamInfo paramInfo = paramInfoMapper.selectById(paramId);
        if (paramInfo != null){
            LOGGER.info("ParamInfoServiceImpl-Sign：{}", paramInfo.getParamSign());
        }else {
            paramInfo = new ParamInfo();
        }
        return paramInfo;
    }

    @Override
    public void insertParam(ParamInfo paramInfo) {
        paramInfoMapper.insertParam(paramInfo);
    }

}
