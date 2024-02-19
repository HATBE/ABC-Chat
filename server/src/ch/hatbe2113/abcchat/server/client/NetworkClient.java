package ch.hatbe2113.abcchat.server.client;

import ch.hatbe2113.abcchat.server.Application;

import java.net.Socket;

public class NetworkClient {
    private Application app;
    private Socket connection;
    private String ipAddress;
    private NetworkClientThread clientThread;

    public NetworkClient(Application app, Socket connection) {
        this.app = app;
        this.connection = connection;

        this.ipAddress = connection.getInetAddress().getHostAddress();

        this.clientThread = new NetworkClientThread(app, this, connection);
    }

    public void sendString(String string) {
        this.clientThread.sendString(string);
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
}