package andreamaiolo.backend.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {
    List<String> messagesLog;

    public ValidationException(List<String> messages) {
        super("Error in validation");
        this.messagesLog = messages;
    }

    public List<String> getMessagesLog() {
        return messagesLog;
    }
}
