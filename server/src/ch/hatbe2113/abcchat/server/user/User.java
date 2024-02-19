package ch.hatbe2113.abcchat.server.user;

import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public class User {
    private String username;
    private NetworkClient client;

    public User(NetworkClient client, String username) {
        this.client = client;
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public NetworkClient getClient() {
        return this.client;
    }
}
