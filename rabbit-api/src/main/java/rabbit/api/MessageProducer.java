package rabbit.api;

import rabbit.api.exception.MessageRunTimeException;

import java.util.List;

public interface MessageProducer {
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;
    void send(Message message) throws MessageRunTimeException;
    void send(List<Message> messages) throws MessageRunTimeException;

}
