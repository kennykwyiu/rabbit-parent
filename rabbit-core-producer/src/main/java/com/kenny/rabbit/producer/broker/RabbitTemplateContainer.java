package com.kenny.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.kenny.rabbit.common.Serializer;
import com.kenny.rabbit.common.Serializerfactory;
import com.kenny.rabbit.common.convert.GenericMessageConverter;
import com.kenny.rabbit.common.convert.RabbitMessageConverter;
import com.kenny.rabbit.common.serializer.impl.JacksonSerializerfactory;
import com.kenny.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import rabbit.api.Message;
import rabbit.api.MessageType;
import rabbit.api.exception.MessageRunTimeException;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String /* TOPIC */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    private Serializerfactory serializerfactory = JacksonSerializerfactory.INSTANCE;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageStoreService messageStoreService;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }

        log.info(getClassAndMethodName() + "topic: {} is not existed, create one", topic);

        RabbitTemplate newRabbitTemplate = getRabbitTemplate(message, topic);

        rabbitMap.put(topic, newRabbitTemplate);

        return rabbitMap.get(topic);
    }

    private RabbitTemplate getRabbitTemplate(Message message, String topic) {
        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());

        Serializer serializer = serializerfactory.create();
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
        newRabbitTemplate.setMessageConverter(rmc);

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
        List<String> strings = splitter.splitToList(correlationData.getId());

        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));

        if (ack) {

            this.messageStoreService.success(messageId);

            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        }
    }
}
