package com.kenny.rabbit.common.serializer.impl;

import com.kenny.rabbit.common.serializer.Serializer;
import com.kenny.rabbit.common.serializer.Serializerfactory;
import rabbit.api.Message;

public class JacksonSerializerfactory implements Serializerfactory {
    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
