package ch.hatbe2113.abcchat.server.messages;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public class MessageHandler {
    Application app;
    public MessageHandler(Application app) {
        this.app = app;
    }

    public void broadcastString(String string) {
        for(NetworkClient client : this.app.getClientManager().getClients()) {
            if(client.getUser() == null) { // filter all not loggedin clients
                continue;
            }
            client.sendString(string);
        }
    }

    public void sendString(NetworkClient client, String string) {
        client.sendString(string);
    }
}
