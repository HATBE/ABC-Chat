package ch.hatbe.protocol.responses;

import ch.hatbe.user.User;

public class LeaveResponse extends Response {
    public User user;

    public LeaveResponse(User user) {
        super(true);
        this.user = user;
    }

    @Override
    public String getType() {
        return "LEAVE";
    }
}
