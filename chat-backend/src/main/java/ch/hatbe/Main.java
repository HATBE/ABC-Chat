package ch.hatbe;

import ch.hatbe.protocol.ActionRegistry;
import ch.hatbe.protocol.actions.LoginAction;
import ch.hatbe.server.TcpServer;

public class Main {
    static void main(String[] args) {
        ActionRegistry.getInstance().registerAction("LOGIN", new LoginAction());

        TcpServer server = new TcpServer(12345);
        server.start();
    }
}
