package ch.hatbe2113.abcchat.server.messages;

import java.util.Date;

public class DisconnectInfoMessage extends Message {
    private int time;
    private String username;

    public DisconnectInfoMessage(String username) {
        this.name = "DISCONNECT";
        this.time = (int) (new Date().getTime() / 1000L);
        this.username = username;
    }
    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return username;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String serialize() {
        return String.format(this.name + this.spacer + this.time + this.spacer + this.username);
    }
}
