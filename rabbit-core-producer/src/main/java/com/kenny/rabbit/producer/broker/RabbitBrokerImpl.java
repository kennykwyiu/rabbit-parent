package com.kenny.rabbit.producer.broker;

import com.kenny.rabbit.producer.constant.BrokerMessageConst;
import com.kenny.rabbit.producer.constant.BrokerMessageStatus;
import com.kenny.rabbit.producer.entity.BrokerMessage;
import com.kenny.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbit.api.Message;
import rabbit.api.MessageType;
import rabbit.api.exception.MessageRunTimeException;

import java.util.Date;

@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    private void sendKernel(Message message) throws MessageRunTimeException {
        AsyncBaseQueue.submit((Runnable) () -> {
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s",
                    message.getMessageId(),
                    System.currentTimeMillis()));
            String topic = message.getTopic();

            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);

            String routingKey = rabbitTemplate.getRoutingKey();
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {

        message.setMessageType(MessageType.RELIANT);
        //1. First record the message sending log in the database
        Date now = new Date();
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(message.getMessageId());
        brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
        //tryCount doesn't need to be set during the initial sending
        brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
        brokerMessage.setCreateTime(now);
        brokerMessage.setUpdateTime(now);
        brokerMessage.setMessage(message);
        messageStoreService.insert(brokerMessage);

        //2. Execute the actual message sending logic
        sendKernel(message);
    }

    @Override
    public void sendMessages() {

    }
}
