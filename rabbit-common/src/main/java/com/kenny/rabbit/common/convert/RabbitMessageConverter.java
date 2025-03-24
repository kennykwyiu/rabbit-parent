package com.kenny.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMessageConverter implements MessageConverter {
    private GenericMessageConverter delegate;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }


    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        rabbit.api.Message msg = (rabbit.api.Message) this.delegate.fromMessage(message);
        return msg;
    }
}
