package ch.hatbe2113.abcchat.client.networkClient;

import java.net.Socket;

public class NetworkClient {
    private Socket clientConnection;

    public NetworkClient(Socket clientConnection) {
        this.clientConnection = clientConnection;
    }

    public String getIpAddress() {
        return this.clientConnection.getInetAddress().getHostAddress();
    }
}
