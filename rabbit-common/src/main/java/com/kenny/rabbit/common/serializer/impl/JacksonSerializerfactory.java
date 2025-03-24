package com.kenny.rabbit.common.serializer.impl;

import com.kenny.rabbit.common.Serializer;
import com.kenny.rabbit.common.Serializerfactory;
import rabbit.api.Message;

public class JacksonSerializerfactory implements Serializerfactory {

    public static final Serializerfactory INSTANCE = new JacksonSerializerfactory();
    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
