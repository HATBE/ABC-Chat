package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.messages.BroadcastMessage;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

import java.util.Date;

public class BroadcastPackage extends NetworkPackage {
    // TODO: rename client to sender
    public BroadcastPackage(Application app, NetworkClient client, String[] data) {
        super(app, client, data);
    }

    @Override
    public void handle() {
        if(this.client.getUser() == null) {
            this.client.getClientThread().getToClientWriter().println("ERR|You are not loggedin");
            return;
        }
        if (data.length != 1) {
            this.client.getClientThread().getToClientWriter().println("ERR|Misformed package");
            return;
        }

        Date date = new Date(2023 - 1900, 1, 15, 0, 0, 0);
        long timestamp = date.getTime() / 1000L;

        BroadcastMessage broadcastMessage = new BroadcastMessage(this.client.getUser().getUsername(), data[0]);

        this.client.sendString(broadcastMessage.ack());
        this.app.getMessageHandler().broadcastString(broadcastMessage.serialize());
    }
}
