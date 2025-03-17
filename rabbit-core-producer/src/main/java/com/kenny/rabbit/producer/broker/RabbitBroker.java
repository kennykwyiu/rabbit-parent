package com.kenny.rabbit.producer.broker;

import rabbit.api.Message;

public interface RabbitBroker {
    void rapidSend(Message message);
    void confirmSend(Message message);
    void reliantSend(Message message);
    void sendMessages();
}
