package com.kenny.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbit.api.Message;
import rabbit.api.MessageProducer;
import rabbit.api.MessageType;
import rabbit.api.SendCallback;
import rabbit.api.exception.MessageRunTimeException;

import java.util.List;

@Component
public class ProducerClient implements MessageProducer {
    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        MessageType messageType = message.getMessageType();
        switch (messageType) {
            case RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(Message message) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }
}
