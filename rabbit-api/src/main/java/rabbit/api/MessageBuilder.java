package rabbit.api;

import rabbit.api.exception.MessageRunTimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageBuilder {
    private String messageId;
    private String topic;
    private String routingKey = "";
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private int delayMills;
    private MessageType messageType = MessageType.CONFIRM;

    private MessageBuilder() {
    }

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder withDelayMills(int delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder withMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() throws MessageRunTimeException {

        if (messageId == null) {
            messageId = UUID.randomUUID().toString();
        }

        if (topic == null) {
            throw new MessageRunTimeException("this topic is null");
        }

        Message message = new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
        return message;
    }
}
