package com.wx.lab.job.jobs;

import com.wx.lab.dubbo.api.RecieveMessageApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:31
 * @projectname totipotent
 */
@Component
public class RecieveMessageJob {

    private static Logger logger = LoggerFactory.getLogger(RecieveMessageJob.class);

    @Reference(version = "1.0.0")
    private RecieveMessageApi recieveMessageApi;

    @XxlJob("messageJobHandler")
    public ReturnT<String> messageJobHandler(String param) throws Exception {
        logger.info("================接收消息订单任务开始==================");
        recieveMessageApi.pullMqMessage();
        logger.info("================接收消息订单任务结束==================");
        return ReturnT.SUCCESS;
    }
}
