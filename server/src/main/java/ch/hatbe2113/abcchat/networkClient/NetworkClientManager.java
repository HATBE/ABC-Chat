package ch.hatbe2113.abcchat.networkClient;

import ch.hatbe2113.abcchat.logger.LogManager;
import ch.hatbe2113.abcchat.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkClientManager {
    private final List<NetworkClient> clients;
    private final Server server;

    public NetworkClientManager(Server server) {
        this.server = server;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void waitForClients() {
        while (!this.server.getServerSocket().isClosed()) {
            LogManager.getLogger().info("Waiting for new client.");

            try {
                // TODO:
                Socket clientConnection = this.server.getServerSocket().accept();
                NetworkClient client = new NetworkClient(clientConnection);
                this.addClient(client);
                LogManager.getLogger().info("Accepted new client {}.", "IPTODO:");
            } catch(IOException e) {
                LogManager.getLogger().error("Something went wrong while client was connecting.", e);
            }
        }
    }

    private void addClient(NetworkClient client) {
        this.clients.add(client);
    }

    public void removeClient(NetworkClient client) {
        this.clients.remove(client);
    }

    public List<NetworkClient> getClients() {
        return this.clients;
    }
}
