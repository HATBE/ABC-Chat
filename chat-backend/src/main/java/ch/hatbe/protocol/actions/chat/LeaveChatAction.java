package ch.hatbe.protocol.actions.chat;

import ch.hatbe.chat.Chat;
import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.AckResponse;
import ch.hatbe.protocol.responses.CreateChatResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveChatAction extends Action {
    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getUser() == null) {
            this.error(session, "You are not logged in!");
            return;
        }

        try {
           session.getContext().getChatManager().leave(session.getClient().getUser());
            session.send(new AckResponse());
        } catch(Exception e) {
            this.error(session, e.getMessage());
        }
    }
}
