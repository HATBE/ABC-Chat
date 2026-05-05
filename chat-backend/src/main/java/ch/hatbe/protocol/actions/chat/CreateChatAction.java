package ch.hatbe.protocol.actions.chat;

import ch.hatbe.chat.Chat;
import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.CreateChatResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateChatAction extends Action  {
    @JsonProperty(required = true)
    @Getter
    private String name;

    @Override
    public void handle(ClientSession session) {
        try {
            Chat chat = session.getContext().getChatManager().create(session.getClient().getUser(), this.name);
            session.send(new CreateChatResponse(chat));
        } catch(Exception e) {
            this.error(session, e.getMessage());
        }
    }
}
