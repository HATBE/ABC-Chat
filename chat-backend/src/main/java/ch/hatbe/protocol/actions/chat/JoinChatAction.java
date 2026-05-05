package ch.hatbe.protocol.actions.chat;

import ch.hatbe.chat.Chat;
import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.CreateChatResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinChatAction extends Action {
    @JsonProperty(required = true)
    @Getter
    private String name;

    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getUser() == null) {
            this.error(session, "You are not logged in!");
            return;
        }

        try {
            Chat chat = session.getContext().getChatManager().join(this.name, session.getClient().getUser());
            session.send(new CreateChatResponse(chat));
        } catch(Exception e) {
            this.error(session, e.getMessage());
        }
    }
}
