package com.kenny.rabbit.producer.broker;

import org.springframework.stereotype.Component;
import rabbit.api.Message;
@Component
public class RabbitBrokerImpl implements RabbitBroker{
    @Override
    public void rapidSend(Message message) {

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
