package com.wx.lab.dubbo.api;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:24
 * @projectname totipotent
 */
public interface RecieveMessageApi {

    /**
     * 拉取队列消息
     * @return
     */
    public int pullMqMessage();
}
