package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

import java.util.Date;

public class MessagePackage extends NetworkPackage {
    // TODO: allow for longer data stuff, because you can add space at the end. does this even make sense? because no, it splits after a pipe, delete this todo, you fucking idiot
    public MessagePackage(Application app, NetworkClient client, String[] data) {
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

        this.client.sendString("MESSAGE|ACK");
        this.app.getMessageHandler().broadcastString(String.format("BROADCAST|%s|%s", timestamp, data[0]));
    }
}
