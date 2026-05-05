package ch.hatbe.protocol.responses;

import ch.hatbe.user.User;

public class JoinResponse extends Response {
    public User user;

    public JoinResponse(User user) {
        super(true);
        this.user = user;
    }

    @Override
    public String getType() {
        return "JOIN";
    }
}
