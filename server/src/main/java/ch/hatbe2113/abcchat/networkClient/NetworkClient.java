package ch.hatbe2113.abcchat.networkClient;

import java.net.Socket;

public class NetworkClient {
    private Socket clientConnection;

    public NetworkClient(Socket clientConnection) {
        this.clientConnection = clientConnection;
    }
}
