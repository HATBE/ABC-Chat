package ch.hatbe2113.abcchat.server.messages;

import java.util.Date;

public class BroadcastMessage extends Message {
    private int time;
    private String username;
    private String message;

    public BroadcastMessage(String username, String message) {
        this.name = "BROADCAST";
        this.username = username;
        this.message = message;
        this.time = (int) (new Date().getTime() / 1000L);
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getMessage() {
        return this.message;
    }
    public int getTime() {
        return this.time;
    }

    @Override
    public String serialize() {
        return String.format(this.name + this.spacer + this.time + this.spacer + this.username + this.spacer + this.message);
    }
}
