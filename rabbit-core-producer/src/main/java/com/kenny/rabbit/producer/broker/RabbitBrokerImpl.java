package com.kenny.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbit.api.Message;
import rabbit.api.MessageType;
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    private void sendKernel(Message message) {
        CorrelationData correlationData = new CorrelationData(String.format("%s#%s",
                                                        message.getMessageId(),
                                                        System.currentTimeMillis()));
        String topic = message.getTopic();
        String routingKey = rabbitTemplate.getRoutingKey();
        rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
        log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
    }

    @Override
    public void confirmSend(Message message) {

    }

    @Override
    public void reliantSend(Message message) {

    }

    @Override
    public void sendMessages() {

    }
}
