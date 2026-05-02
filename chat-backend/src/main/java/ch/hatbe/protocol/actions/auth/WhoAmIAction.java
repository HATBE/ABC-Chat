package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.protocol.responses.WhoAmIResponse;
import ch.hatbe.server.client.ClientSession;
import ch.hatbe.server.client.LoginState;
import ch.hatbe.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoAmIAction extends Action {
    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getLoginState() != LoginState.LOGGED_IN) {
            session.send(new ErrorResponse("You are not Logged In!").toJson());
            return;
        }

        User user = session.getClient().getUser();

        if (user == null) {
            session.send(new ErrorResponse("You are not Logged In!").toJson());
            return;
        }

        session.send(new WhoAmIResponse(user).toJson());
    }
}
