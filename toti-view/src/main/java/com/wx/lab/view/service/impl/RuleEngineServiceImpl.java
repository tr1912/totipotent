package com.wx.lab.view.service.impl;

import com.wx.lab.view.config.SpringContextUtil;
import com.wx.lab.view.po.ParamInfo;
import com.wx.lab.view.entity.QueryParam;
import com.wx.lab.view.service.ParamInfoService;
import com.wx.lab.view.service.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RuleEngineServiceImpl implements RuleEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineServiceImpl.class);

    @Override
    public void executeAddRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        ParamInfo paramInfo = new ParamInfo();
        paramInfo.setId(param.getParamId());
        paramInfo.setParamSign(param.getParamSign());
        paramInfo.setCreateTime(new Date());
        paramInfo.setUpdateTime(new Date());
        ParamInfoService paramInfoService = (ParamInfoService) SpringContextUtil.getBean("paramInfoService");
        paramInfoService.insertParam(paramInfo);
    }

    @Override
    public void executeRemoveRule(QueryParam param) {
        LOGGER.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        ParamInfoService paramInfoService = (ParamInfoService) SpringContextUtil.getBean("paramInfoService");
        ParamInfo paramInfo = paramInfoService.selectById(param.getParamId());
        if (paramInfo != null) {
            paramInfoService.removeById(param.getParamId());
        }
    }
}
