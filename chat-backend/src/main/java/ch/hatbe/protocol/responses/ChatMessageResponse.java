package ch.hatbe.protocol.responses;

import ch.hatbe.user.User;

public class ChatMessageResponse extends Response {
    public User user;
    public String message;

    public ChatMessageResponse(User user, String message) {
        super(true);
        this.user = user;
        this.message = message;
    }

    @Override
    public String getType() {
        return "CHAT_MESSAGE";
    }
}
