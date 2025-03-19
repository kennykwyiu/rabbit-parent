package rabbit.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 841277940410721237L;

    private String messageId;

    /*  Message topic  */
    private String topic;

    /*  Message routing rule  */
    private String routingKey = "";

    /*  Additional message attributes  */
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /*  Delay message parameter configuration  */
    private int delayMills;

    /*  Message type: Default is confirm message type  */
    private MessageType messageType = MessageType.CONFIRM;

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills,
                   MessageType messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }

}
