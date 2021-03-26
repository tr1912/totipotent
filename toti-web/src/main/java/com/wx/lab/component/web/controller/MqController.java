package com.wx.lab.component.web.controller;

import com.wx.lab.component.base.common.MessageResult;
import com.wx.lab.component.service.MqService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-18 下午5:19
 * @projectname totipotent
 */
@RestController
@RequestMapping("/api/mq")
public class MqController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private MqService mqService;

    public MessageResult sendMessage(String msg){
        boolean flag = mqService.sendMessageByString(msg);
        return MessageResult.build("1", "success");
    }
}
