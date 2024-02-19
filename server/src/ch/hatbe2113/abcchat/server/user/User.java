package ch.hatbe2113.abcchat.server.user;

import ch.hatbe2113.abcchat.server.client.NetworkClient;

public class User {
    private String username;
    private NetworkClient client;

    public User(NetworkClient client) {
        this.client = client;
        this.username = null;
    }


}
