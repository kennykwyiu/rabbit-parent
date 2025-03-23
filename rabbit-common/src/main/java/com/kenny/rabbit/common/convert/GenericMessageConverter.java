package com.kenny.rabbit.common.convert;

import com.google.common.base.Preconditions;
import com.kenny.rabbit.common.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.lang.reflect.Type;

public class GenericMessageConverter implements MessageConverter {
    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }


    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties, Type genericType) throws MessageConversionException {
        return MessageConverter.super.toMessage(object, messageProperties, genericType);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] body = message.getBody();
        return this.serializer.deserialize(body);
    }
}
