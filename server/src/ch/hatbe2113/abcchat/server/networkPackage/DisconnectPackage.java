package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.messages.DisconnectInfoMessage;
import ch.hatbe2113.abcchat.server.messages.JoinInfoMessage;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public class DisconnectPackage extends NetworkPackage {
    public DisconnectPackage(Application app, NetworkClient client, String[] data) {
        super(app, client, data);
    }

    @Override
    public void handle() {
        if(this.client.getUser() == null) {
            this.client.getClientThread().getToClientWriter().println("ERR|You are not loggedin");
            return;
        }
        if (data.length != 0) {
            this.client.getClientThread().getToClientWriter().println("ERR|Misformed package");
            return;
        }

        DisconnectInfoMessage disconnectInfo = new DisconnectInfoMessage(this.client.getUser().getUsername());

        this.client.sendString(disconnectInfo.ack());
        this.app.getMessageHandler().broadcastString(disconnectInfo.serialize());

        this.client.disconnect();
    }
}
