package com.wx.lab.component.service;

import org.springframework.messaging.Message;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-19 下午6:14
 * @projectname totipotent
 */
public interface MqService {

    /**
     * 发送string类型消息
     * @param message
     * @return
     */
    boolean sendMessageByString(String message);
}
