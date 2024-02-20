package ch.hatbe2113.abcchat.server.networkPackage;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.networkClient.NetworkClient;

public class PackageHandler {
    Application app;
    NetworkClient client;

    public PackageHandler(Application app, NetworkClient client) {
        this.app = app;
        this.client = client;
    }

    public void handlePackage(String data) {
        String[] dataSplit = data.split("\\|");

        if(dataSplit.length < 1) {
            return;
        }

        String packageName = dataSplit[0];
        String[] newDataSplit = new String[dataSplit.length - 1];
        System.arraycopy(dataSplit, 1, newDataSplit, 0, dataSplit.length - 1);

        switch (packageName) {
            case "JOIN" -> {
                new JoinPackage(this.app, this.client, newDataSplit).handle();
            }
            case "DISCONNECT" -> {
                new DisconnectPackage(this.app, this.client, newDataSplit).handle();
            }
            case "BROADCAST" -> {
                new BroadcastPackage(this.app, this.client, newDataSplit).handle();
            }
            default -> this.client.getClientThread().getToClientWriter().println("ERR|unknown package");
        }
    }
}
