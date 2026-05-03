package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.protocol.responses.LoginResponse;
import ch.hatbe.server.client.ClientSession;
import ch.hatbe.user.User;
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

        // YES; NO PW!

        // TODO: check if username is already used // maybe later make login system or so with pw

        try {
            this.validateUsername(username);
        } catch(Exception e) {
            this.error(session, e.getMessage());
        }

        User user = new User(this.username.strip());
        session.getClient().setUser(user);

        session.send(new LoginResponse(user).toJson());
    }

    private void validateUsername(String username) throws Exception {
        if (username == null || username.isBlank()) {
            throw new Exception("Username is required");
        }

        if (username.length() < 3 || username.length() > 16) {
            throw new Exception("The length of the Username must be between 3 and 16 characters");
        }
    }
}
