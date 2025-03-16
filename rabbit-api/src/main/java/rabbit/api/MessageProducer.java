package rabbit.api;

import rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * MessageProducer is an abstraction of the RabbitMQ producer API.
 * It provides methods for sending messages to a message broker.
 */
public interface MessageProducer {

    /**
     * Sends a message asynchronously with a callback.
     * The callback provides confirmation of message delivery.
     *
     * @param message      The message to be sent.
     * @param sendCallback The callback to handle success or failure events.
     * @throws MessageRunTimeException if an error occurs during message sending.
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

    /**
     * Sends a message synchronously without a callback.
     * This method is typically used when message confirmation is not required.
     *
     * @param message The message to be sent.
     * @throws MessageRunTimeException if an error occurs during message sending.
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * Sends a batch of messages in a single operation.
     * Useful for improving performance when sending multiple messages at once.
     *
     * @param messages The list of messages to be sent.
     * @throws MessageRunTimeException if an error occurs during message sending.
     */
    void send(List<Message> messages) throws MessageRunTimeException;
}

