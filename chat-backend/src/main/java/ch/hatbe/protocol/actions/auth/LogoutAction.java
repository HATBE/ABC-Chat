package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.AckResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoutAction extends Action {
    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getUser() == null) {
            this.error(session, "You are not logged in!");
            return;
        }

        session.getClient().setUser(null);
        session.send(new AckResponse().toJson());
    }
}
