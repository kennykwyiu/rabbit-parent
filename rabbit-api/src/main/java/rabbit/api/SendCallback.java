package rabbit.api;

public interface SendCallback {
    void onSuccess();
    void onFailure();
}
