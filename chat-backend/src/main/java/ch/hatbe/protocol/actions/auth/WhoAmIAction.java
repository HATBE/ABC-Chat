package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.WhoAmIResponse;
import ch.hatbe.server.client.ClientSession;
import ch.hatbe.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoAmIAction extends Action {
    @Override
    public void handle(ClientSession session) {
        User user = session.getClient().getUser();

        if (user == null) {
           this.error(session, "You are not Logged In!");
            return;
        }

        session.send(new WhoAmIResponse(user));
    }
}
