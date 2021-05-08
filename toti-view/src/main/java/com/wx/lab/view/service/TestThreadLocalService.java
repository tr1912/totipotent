package com.wx.lab.view.service;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-21 15:57
 * @packagename com.wx.lab.view.service
 */
public interface TestThreadLocalService {

    /**
     * 测试调用方法
     *
     * @param param
     * @return
     */
    String ServiceInvokeExecute(String param) throws InterruptedException;

}
