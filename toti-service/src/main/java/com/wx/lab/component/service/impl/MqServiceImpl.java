package com.wx.lab.component.service.impl;

import com.wx.lab.component.base.common.Result;
import com.wx.lab.component.base.constent.MqTopicConstent;
import com.wx.lab.component.base.emumerate.ResultCode;
import com.wx.lab.component.mq.SendMq;
import com.wx.lab.component.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-19 下午6:15
 * @projectname totipotent
 */
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private SendMq sendMq;

    /**
     * 发送string类型消息
     *
     * @param message
     * @return
     */
    @Override
    public boolean sendMessageByString(String message) {
        Result result = sendMq.sendMessage(MqTopicConstent.SEND_HELLOW_TOPIC, "", "no1", message, "发送Helloween");
        if (result.getCode() == ResultCode.SUCCESS.getCode()){
            return true;
        }
        return false;
    }
}
