package rabbit.api;

/**
 * MessageListener is a callback interface for handling incoming RabbitMQ messages.
 * Implementations of this interface should define logic to process received messages.
 */
public interface MessageListener {

    /**
     * Called when a new message is received.
     * Implementations should handle message processing, such as parsing or persisting data.
     *
     * @param message the received message
     */
    void onMessage(Message message);
}

