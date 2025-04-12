package com.kenny.rabbit.producer.service;

import com.kenny.rabbit.producer.constant.BrokerMessageStatus;
import com.kenny.rabbit.producer.entity.BrokerMessage;
import com.kenny.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageStoreServiceImpl implements MessageStoreService {
    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    @Override
    public int insert(BrokerMessage brokerMessage) {
        return this.brokerMessageMapper.insert(brokerMessage);
    }

    @Override
    public void success(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                                                            BrokerMessageStatus.SEND_OK.getCode(),
                                                            new Date());
    }

    @Override
    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                BrokerMessageStatus.SEND_FAIL.getCode(),
                new Date());
    }
}
