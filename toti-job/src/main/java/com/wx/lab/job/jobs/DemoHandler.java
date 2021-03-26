package com.wx.lab.job.jobs;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DemoHandler extends IJobHandler {

    @Autowired
    private DemoJobService demoJobService;

    @XxlJob(value = "printLine")
    @Override
    public ReturnT<String> execute(String s1) throws Exception {
        XxlJobLogger.log("xxl-job测试任务开始执行。【args: {}】", s1);
        try {
            demoJobService.demoTest(s1);
            XxlJobLogger.log("xxl-job测试任务执行结束。");
            return SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log("xxl-job测试任务执行出错!", e);
            return FAIL;
        }
    }
}
