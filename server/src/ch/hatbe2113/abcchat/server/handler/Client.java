package ch.hatbe2113.abcchat.server.handler;

import ch.hatbe2113.abcchat.server.Application;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Application app;
    private Socket connection;
    private String username;
    private String ipAddress;
    private ClientThread clientThread;

    public Client(Application app, Socket connection) {
        this.app = app;
        this.connection = connection;

        this.username = "undefined";
        this.ipAddress = connection.getInetAddress().getHostAddress();

        this.clientThread = new ClientThread(app, this, connection);
    }

    public void sendMessage(String message) {
        this.clientThread.sendMessage(message);
    }

    public String getUsername() {
        return this.username;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
}
