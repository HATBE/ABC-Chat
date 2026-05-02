package ch.hatbe.protocol.actions.auth;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.protocol.responses.LoginResponse;
import ch.hatbe.server.client.ClientSession;
import ch.hatbe.server.client.LoginState;
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
        if (session.getClient().getLoginState() == LoginState.LOGGED_IN) {
            session.send(new ErrorResponse("You are already logged in!").toJson());
            return;
        }

        // YES; NO PW!

        // TODO: check if username is already used // maybe later make login system or so with pw

        User user = new User(this.username);
        session.getClient().setUser(user);
        session.getClient().setLoginState(LoginState.LOGGED_IN);

        session.send(new LoginResponse(user).toJson());
    }
}
