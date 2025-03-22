package com.kenny.rabbit.producer.broker;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String /* TOPIC */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }
}
