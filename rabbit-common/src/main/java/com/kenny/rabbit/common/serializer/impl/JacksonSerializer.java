package com.kenny.rabbit.common.serializer.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kenny.rabbit.common.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

public class JacksonSerializer implements Serializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonSerializer.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    private final JavaType type;

    public JacksonSerializer(JavaType type) {
        this.type = type;
    }

    public JacksonSerializer(Type type) {
        this.type = mapper.getTypeFactory().constructType(type);
    }

    public static JacksonSerializer createParametricType(Class<?> clazz) {
        return new JacksonSerializer(mapper.getTypeFactory().constructType(clazz));
    }

    @Override
    public byte[] serializeRaw(Object data) {
        try {
            return mapper.writeValueAsBytes(data); // Convert object to byte array
        } catch (JsonProcessingException e) {
            LOGGER.error("Serialization error", e);
        }
        return null; // Return null if serialization fails
    }

    @Override
    public String serialize(Object data) {
        try {
            return mapper.writeValueAsString(data); // Convert object to JSON string
        } catch (JsonProcessingException e) {
            LOGGER.error("Serialization error", e);
        }
        return null; // Return null if serialization fails
    }

    @Override
    public <T> T deserialize(String content) {
        try {
            return mapper.readValue(content, type); // Convert JSON string back to object
        } catch (IOException e) {
            LOGGER.error("Deserialization error", e);
        }
        return null; // Return null if deserialization fails
    }

    @Override
    public <T> T deserialize(byte[] content) {
        return null;
    }
}
