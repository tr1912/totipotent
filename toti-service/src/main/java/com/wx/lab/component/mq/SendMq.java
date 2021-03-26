package com.wx.lab.component.mq;

import com.alibaba.fastjson.JSON;
import com.wx.lab.component.base.common.Result;
import com.wx.lab.component.base.emumerate.ResultCode;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by songzhaoying on 2020/8/4 11:58.
 *
 * @author songzhaoying@ybm100.com.
 * @date 2020/8/4 11:58.
 */
@Component
public class SendMq {

    private static final Logger logger = LoggerFactory.getLogger(SendMq.class);

    private static final String MQ_MODULE_NAME = "pBill";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.topic.ledger.process}")
    private String prefix;


    /**
     * @param topic         必输
     * @param tags          非必输
     * @param keys          必输
     * @param msgBodyObject 消息对象 必输
     * @param description   必输
     * @return
     */
    public Result sendMessage(String topic, String tags, String keys, Object msgBodyObject, String description) {

        if (StringUtils.isEmpty(topic)
                || StringUtils.isEmpty(keys)
                || msgBodyObject == null
                || StringUtils.isEmpty(description)) {
            return new Result<>(ResultCode.PARAM_ILLEGAL.getCode(), ResultCode.PARAM_ILLEGAL.getMsg());
        }
        //构建消息
        Message message = MessageBuilder
                .withPayload(msgBodyObject)
                .setHeader(RocketMQHeaders.KEYS, keys)
                .setHeader(RocketMQHeaders.TAGS, tags)
                .setHeader(RocketMQHeaders.TOPIC, topic)
                .build();

        SendResult sendResult = null;

        try {
            //发送消息
            sendResult = rocketMQTemplate.syncSend(prefix + topic + ":" + (null == tags ? "" : tags), message);

            if (null != sendResult && sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                logger.info("发送消息成功！sendResult:{}", JSON.toJSONString(sendResult));
                return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
            } else {
                logger.info("mq 发送失败，写入待发送序列！");
            }

        } catch (Exception e) {
            logger.error("发送MQ消息异常,异常信息是topi={} and keys={} and tags={} and moduleName={} and description={}"
                    , topic, keys, tags, MQ_MODULE_NAME, description, e);
        }

        return new Result<>(ResultCode.SERVICE_FAILURE.getCode(), ResultCode.SERVICE_FAILURE.getMsg());
    }
}
