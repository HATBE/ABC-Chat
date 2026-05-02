package ch.hatbe.protocol.responses;

import ch.hatbe.user.User;

public class LoginResponse extends Response{
    public User user;

    public LoginResponse(User user) {
        super(true);
        this.user = user;
    }

    @Override
    public String getType() {
        return "LOGIN";
    }
}
