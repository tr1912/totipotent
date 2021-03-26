package com.wx.lab.dubbo.service;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:25
 * @projectname totipotent
 */
public interface RecieveMessageService {

    /**
     * 拉取mq消息
     * @return
     */
    public int pullMqMessage();
}
