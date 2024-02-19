package ch.hatbe2113.abcchat.server;

import ch.hatbe2113.abcchat.server.client.NetworkClientManager;
import ch.hatbe2113.abcchat.server.server.Server;


public class Application {
    private Server server;
    private NetworkClientManager clientManager;

    public Application() {
        this.server = new Server(1111);

        if(!this.server.startServer()) {
            return;
        }

        this.clientManager = new NetworkClientManager(this);
        this.clientManager.waitForClients();

        this.server.stopServer();
    }

    public Server getServer() {
        return server;
    }

    public NetworkClientManager getClientManager() {
        return clientManager;
    }
}