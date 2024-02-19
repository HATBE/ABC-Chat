package ch.hatbe2113.abcchat.server.networkClient;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.user.User;

import java.net.Socket;

public class NetworkClient {
    private Application app;
    private Socket connection;
    private String ipAddress;
    private NetworkClientThread clientThread;
    private User user;

    public NetworkClient(Application app, Socket connection) {
        this.app = app;
        this.connection = connection;

        this.user = null;

        this.ipAddress = connection.getInetAddress().getHostAddress();

        this.clientThread = new NetworkClientThread(app, this, connection);
    }

    public void sendString(String string) {
        this.clientThread.sendString(string);
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public NetworkClientThread getClientThread() {
        return clientThread;
    }

    public User getUser() {
        return user;
    }

    public void createUser(String username) {
        this.user = new User(this, username);
    }

    public void disconnect() {
        this.getClientThread().disconnect();
    }
}