package com.wx.lab.dubbo.api.impl;

import com.wx.lab.dubbo.api.RecieveMessageApi;
import com.wx.lab.dubbo.service.RecieveMessageService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:25
 * @projectname totipotent
 */
@Service(version = "1.0.0")
public class RecieveMessageApiImpl implements RecieveMessageApi {

    @Autowired
    private RecieveMessageService recieveMessageService;

    @Override
    public int pullMqMessage() {
        return recieveMessageService.pullMqMessage();
    }
}
