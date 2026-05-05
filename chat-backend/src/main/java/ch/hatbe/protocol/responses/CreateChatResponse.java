package ch.hatbe.protocol.responses;

import ch.hatbe.chat.Chat;

public class CreateChatResponse extends Response {
    public Chat chat;

    public CreateChatResponse(Chat chat) {
        super(true);
        this.chat = chat;
    }

    @Override
    public String getType() {
        return "CREATE_CHAT";
    }
}
