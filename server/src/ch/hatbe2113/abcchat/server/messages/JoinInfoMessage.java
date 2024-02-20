package ch.hatbe2113.abcchat.server.messages;

import java.util.Date;

public class JoinInfoMessage extends Message {
    private int time;
    private String username;
    public JoinInfoMessage(String username) {
        this.name = "JOIN";
        this.time = (int) (new Date().getTime() / 1000L);
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public String serialize() {
        return String.format(this.name + this.spacer + this.time + this.spacer + this.username);
    }
}
