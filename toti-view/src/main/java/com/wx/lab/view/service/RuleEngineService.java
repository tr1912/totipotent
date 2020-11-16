package com.wx.lab.view.service;

import com.wx.lab.view.entity.QueryParam;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-13 10:41
 * @projectname totipotent
 */
public interface RuleEngineService {
    void executeAddRule(QueryParam param);

    void executeRemoveRule(QueryParam param);
}
