package ch.hatbe;

import ch.hatbe.cli.CliArguments;
import ch.hatbe.protocol.ActionRegistry;
import ch.hatbe.protocol.actions.LoginAction;
import ch.hatbe.server.TcpServer;

public class Main {
    static void main(String[] appArguments) {
        Main.registerArgs();

        if (!CliArguments.getInstance().parse(appArguments)) {
            return;
        }

        Main.registerActions();

        TcpServer server = new TcpServer(CliArguments.getInstance().getInt("port", 12345));
        server.start();
    }

    private static void registerArgs() {
        var cli = CliArguments.getInstance();

        cli.registerOption("p", "port", "The TCP port used to start the chatserver", false);
    }

    private static void registerActions() {
        var ar = ActionRegistry.getInstance();

        ar.registerAction("LOGIN", new LoginAction());
    }
}
