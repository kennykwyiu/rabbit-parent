package com.kenny.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import rabbit.api.Message;
import rabbit.api.MessageType;
import rabbit.api.exception.MessageException;

import java.util.Map;

@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String /* TOPIC */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    @Autowired
    private ConnectionFactory connectionFactory;

    private RabbitTemplate getRabbitTemplate(Message message, String topic) {
        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());

//        newRabbitTemplate.setMessageConverter(messageConverter);

        MessageType messageType = message.getMessageType();
        MessageType rapid = MessageType.RAPID;
        if (!rapid.equals(messageType)) {
            newRabbitTemplate.setConfirmCallback(this);
        }
        return newRabbitTemplate;
    }

    private String getClassAndMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2]; // index 2 gives the caller method
        return "#" + element.getClassName() + "." + element.getMethodName() + "#";
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }
}
