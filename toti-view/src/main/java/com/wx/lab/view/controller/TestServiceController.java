package com.wx.lab.view.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wx.lab.view.service.TestThreadLocalService;
import com.wx.lab.view.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-21 16:10
 * @packagename com.wx.lab.view.controller
 */
@Slf4j
@RestController
@RequestMapping("/test/service")
public class TestServiceController {

    @Autowired
    private TestThreadLocalService testThreadLocalService;

    // 定长线程池
    private ThreadFactory contentThreadFactory = new ThreadFactoryBuilder().setNameFormat("service-invoke-pool-%d").build();
    protected ExecutorService contentExecutor = new ThreadPoolExecutor(100, 200, 70, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200*10000), contentThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 测试单个调用
     *
     * @param param
     * @return
     */
    @GetMapping("/testInvoke")
    @ResponseBody
    public String testService(String param) {
        try {
            return testThreadLocalService.ServiceInvokeExecute(param);
        } catch (Exception e) {
            log.error("调用出现问题！", e);
        }
        return "error";
    }

    /**
     * 测试多线程调用
     *
     * @param param
     * @return
     */
    @GetMapping("/testServiceThread")
    @ResponseBody
    public String testServiceThread(Integer init, String param) {
        if (init == null || init <= 0){
            init = 200000;
        }
        CountDownLatch countDownLatch = new CountDownLatch(init);
        for (int i = 0; i < init; i++) {
            contentExecutor.submit(()->{
                try {
                    HttpClientUtil client = new HttpClientUtil();
                    String url = "http://localhost:8080/test/service/testInvoke?param=abc";
                    client.doGet(url,null);
                    testThreadLocalService.ServiceInvokeExecute(param);
                }catch (Exception e){
                    log.info("当前线程：{}", Thread.currentThread().getName());
                    log.error("线程出现问题！", e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        }catch (Exception e){
            log.error("等待线程出现问题！", e);
        }
        return "success";
    }
}
