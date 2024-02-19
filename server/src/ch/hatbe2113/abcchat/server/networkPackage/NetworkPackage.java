package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public abstract class NetworkPackage {
    protected Application app;
    protected NetworkClient client;
    protected String[] data;

    public NetworkPackage(Application app, NetworkClient client, String[] data) {
        this.app = app;
        this.client = client;
        this.data = data;
    }

    public abstract void handle();
}
