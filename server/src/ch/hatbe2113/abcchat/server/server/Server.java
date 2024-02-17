package ch.hatbe2113.abcchat.server.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public boolean startServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.printf("Started server on port %s.\n", this.port);
            return true;
        } catch (IOException e) {
            System.err.println("Could not start server!");
            e.printStackTrace();
            return false;
        }
    }

    public void stopServer() {
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

}
