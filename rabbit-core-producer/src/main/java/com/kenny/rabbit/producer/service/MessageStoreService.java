package com.kenny.rabbit.producer.service;

import com.kenny.rabbit.producer.entity.BrokerMessage;
import org.springframework.stereotype.Service;

public interface MessageStoreService {
    public int insert(BrokerMessage brokerMessage);
}
