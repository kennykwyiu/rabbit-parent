package com.kenny.rabbit.producer.broker;

import rabbit.api.Message;
import rabbit.api.MessageProducer;
import rabbit.api.SendCallback;
import rabbit.api.exception.MessageRunTimeException;

import java.util.List;

public class ProducerClient implements MessageProducer {
    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }
}
