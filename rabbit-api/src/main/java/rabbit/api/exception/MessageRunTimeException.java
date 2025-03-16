package rabbit.api.exception;

public class MessageRunTimeException extends MessageException{
    private static final long serialVersionUID = -5333110268605928292L;
    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
