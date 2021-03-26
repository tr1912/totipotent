package com.wx.lab.dubbo.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:49
 * @projectname totipotent
 */
@Service
@RocketMQMessageListener(consumerGroup = "toti-web", topic = "devsend_hellow_topic", consumeThreadMax = 1)
public class StringMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static Logger logger = LoggerFactory.getLogger(StringMessageConsumer.class);

    @Override
    public void onMessage(MessageExt message) {
        logger.info("------- LedgerMessageProcess MessageExtConsumer received message, msgId:%s, body:%s %n ",
                message.getMsgId(), new String(message.getBody()));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : msgs) {
                    String messageBody = new String(messageExt.getBody());
                    try {
//                        StorageLossRequestDto lossRequest = JSON.parseObject(messageBody, StorageLossRequestDto.class);
//                        processRestructure(lossRequest);
                    } catch (Exception e) {
                        logger.error("StorageLedgerConsumer-->processRestructure异常", e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
