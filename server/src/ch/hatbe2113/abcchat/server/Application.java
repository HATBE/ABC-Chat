package ch.hatbe2113.abcchat.server;

import ch.hatbe2113.abcchat.server.client.ClientManager;
import ch.hatbe2113.abcchat.server.server.Server;


public class Application {
    private Server server;
    private ClientManager clientManager;

    public Application() {
        this.server = new Server(1111);

        if(!this.server.startServer()) {
            return;
        }

        this.clientManager = new ClientManager(this);
        this.clientManager.waitForClients();

        this.server.stopServer();
    }

    public Server getServer() {
        return server;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }
}
