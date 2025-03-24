package com.kenny.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;

public class RabbitMessageConverter implements MessageConverter {
    private GenericMessageConverter delegate;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Object fromMessage(Message<?> message, Class<?> aClass) {
        return null;
    }

    @Override
    public Message<?> toMessage(Object o, MessageHeaders messageHeaders) {
        return null;
    }
}
