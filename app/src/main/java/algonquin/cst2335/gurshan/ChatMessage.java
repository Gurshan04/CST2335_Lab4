package algonquin.cst2335.gurshan;

public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean isSent;

    public ChatMessage(String message, String timeSent, boolean isSent) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSent() {
        return isSent;
    }
}
