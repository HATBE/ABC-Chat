package ch.hatbe2113.abcchat.server;

import ch.hatbe2113.abcchat.server.client.ClientManager;

import java.io.IOException;
import java.net.ServerSocket;

public class Application {
    private ClientManager clientManager;
    private ServerSocket serverSocket;

    public Application() {
        int port = 1111;

        if(!this.startServer(port)) {
            return;
        }

        this.clientManager = new ClientManager(this);
        this.clientManager.waitForClients();

        this.stopServer();
    }

    private boolean startServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.printf("Started server on port %s.\n", port);
            return true;
        } catch (IOException e) {
            System.err.println("Could not start server!");
            e.printStackTrace();
            return false;
        }
    }

    private void stopServer() {
        if(serverSocket == null) {
            return;
        }

        try {
            this.serverSocket.close();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.err.println("Could not stop server!");
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }
}
