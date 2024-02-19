package ch.hatbe2113.abcchat.server.networkClient;

import ch.hatbe2113.abcchat.server.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkClientManager {
    Application app;
    List<NetworkClient> clients;

    public NetworkClientManager(Application app) {
        this.app = app;
        this.clients = new CopyOnWriteArrayList<NetworkClient>();
    }

    public void waitForClients() {
        while(!this.app.getServer().getServerSocket().isClosed()) {
            System.out.println("Waiting for clients...");
            try {
                Socket clientConnection = this.app.getServer().getServerSocket().accept();
                NetworkClient client = new NetworkClient(this.app, clientConnection);
                this.addClient(client);
                System.out.printf("Accepted new client: %s.\n", client.getIpAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(NetworkClient client) {
        this.clients.add(client);
    }

    public void removeClient(NetworkClient client) {
        this.clients.remove(client);
    }

    public List<NetworkClient> getClients() {
        return this.clients;
    }
}