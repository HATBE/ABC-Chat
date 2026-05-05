package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.protocol.responses.LoginResponse;
import ch.hatbe.server.client.ClientSession;
import ch.hatbe.user.User;
import ch.hatbe.user.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginAction extends Action {
    @JsonProperty(required = true)
    @Getter
    private String username;

    @Override
    public void handle(ClientSession session) {
        if (session.getClient().getUser() != null) {
            this.error(session, "You are already logged in!");
            return;
        }

        try {
            User user = session.getContext().getUserService().login(username, session.getContext(), session);
            session.getClient().setUser(user);
            session.send(new LoginResponse(user));
        } catch(Exception e) {
            this.error(session, e.getMessage());
            return;
        }
    }
}
