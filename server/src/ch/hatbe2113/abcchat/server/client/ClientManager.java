package ch.hatbe2113.abcchat.server.client;

import ch.hatbe2113.abcchat.server.Application;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {
    Application app;

    List<Client> clients;

    public ClientManager(Application app) {
        this.app = app;

        this.clients = new CopyOnWriteArrayList<Client>();
    }

    public void waitForClients() {
        while(true) {
            System.out.println("Waiting for Clients.");
            try {
                Socket clientConnection = this.app.getServer().getServerSocket().accept();
                Client client = new Client(this.app, clientConnection);
                this.addClient(client);
                System.out.printf("Accepted new Client: %s as %s.\n", client.getIpAddress(), client.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String message) {
        for(Client client : this.clients) {
            client.sendMessage(message);
        }
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public List<Client> getClients() {
        return this.clients;
    }
}
