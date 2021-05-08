package com.wx.lab.view.service.impl;

import com.wx.lab.view.annotation.ServiceInvocationLogger;
import com.wx.lab.view.service.TestThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-21 15:57
 * @packagename com.wx.lab.view.service.impl
 */
@Slf4j
@Service
public class TestThreadLocalServiceImpl implements TestThreadLocalService {

    /**
     * 测试调用方法
     *
     * @param param
     * @return
     */
    @ServiceInvocationLogger
    @Override
    public String ServiceInvokeExecute(String param) throws InterruptedException {
        log.info("测试方法开始执行，执行参数：{}", param);
        Random random = new Random();
        Thread.sleep(200);
        log.info("测试方法执行结束，执行时间：{}", 200);
        return String.valueOf(200);
    }
}
