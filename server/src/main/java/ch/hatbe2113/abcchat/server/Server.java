package ch.hatbe2113.abcchat.server;

import ch.hatbe2113.abcchat.console.ConsoleServer;
import ch.hatbe2113.abcchat.logger.LogManager;
import ch.hatbe2113.abcchat.client.networkClient.NetworkClientManager;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private boolean running = false;
    private final int port;
    private ServerSocket serverSocket;
    private final NetworkClientManager clientManager;

    public Server(int port) {
        this.port = port;
        this.clientManager = new NetworkClientManager(this);
    }

    public void start() {
        if(serverSocket != null) {
            return;
        }

        try {
            this.serverSocket = new ServerSocket(this.port);
            this.running = true;
            LogManager.getLogger().info("Started server on port {}.", this.port);
        } catch (IOException e) {
            LogManager.getLogger().error("Could not start the server.", e);
        }
    }

    public void stop() {
        if(serverSocket == null || this.serverSocket.isClosed()) {
            return;
        }

        try {
            this.serverSocket.close();
            this.running = false;
            System.out.println("Server stopped.");
        } catch (IOException e) {
            LogManager.getLogger().error("Could not stop server!", e);
        }
    }

    public void waitForClients() {
        this.clientManager.waitForClients();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isRunning() {
        return running && !serverSocket.isClosed();
    }
}