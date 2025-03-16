package rabbit.api;

public interface MessageListener {
    void onMessage(Message message);
}
