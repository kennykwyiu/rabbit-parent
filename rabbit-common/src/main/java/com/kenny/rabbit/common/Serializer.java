package com.kenny.rabbit.common;

public interface Serializer {
    byte[] serializeRaw(Object data);
    String serialize(Object data);
    <T> T deserialize(String content);
    <T> T deserialize(byte[] content);

}
