package ch.hatbe.protocol.actions.chat;

import ch.hatbe.chat.Chat;
import ch.hatbe.protocol.actions.Action;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageAction extends Action {
    @JsonProperty(required = true)
    @Getter
    private String message;

    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getUser() == null) {
            this.error(session, "You are not logged in!");
            return;
        }

        try {
            Chat chat = session.getContext().getChatManager().getCurrentChat(session.getClient().getUser());

            if(chat == null) {
                this.error(session, "You are not in a chat");
                return;
            }

            chat.sendMessage(this.message, session.getClient().getUser());
        } catch(Exception e) {
            this.error(session, e.getMessage());
        }
    }
}
