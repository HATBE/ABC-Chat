package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.messages.JoinInfoMessage;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public class JoinPackage extends NetworkPackage {

    public JoinPackage(Application app, NetworkClient client, String[] data) {
        super(app, client, data);
    }

    @Override
    public void handle() {
        if(this.client.getUser() != null) {
            this.client.getClientThread().getToClientWriter().println("ERR|You are already loggedin");
            return;
        }
        if (this.data.length != 1) {
            this.client.sendString("ERR|Misformed package");
            return;
        }

        String username = this.data[0];

        if(this.app.getClientManager().usernameExists(username)) {
            this.client.sendString("ERR|Username already taken");
            return;
        }

        this.client.createUser(username);

        JoinInfoMessage joinInfo = new JoinInfoMessage(username);

        this.client.sendString(joinInfo.ack());
        this.app.getMessageHandler().broadcastString(joinInfo.serialize());
    }
}
