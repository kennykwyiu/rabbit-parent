package rabbit.api;

/**
 * SendCallback provides a mechanism for handling asynchronous message delivery results.
 * It defines callback methods for success and failure scenarios when sending messages.
 */
public interface SendCallback {

    /**
     * Called when the message is successfully sent.
     * This method should contain logic to handle a successful message delivery.
     */
    void onSuccess();

    /**
     * Called when the message fails to send.
     * This method should contain logic to handle failure scenarios,
     * such as logging errors or retrying message delivery.
     */
    void onFailure();
}

