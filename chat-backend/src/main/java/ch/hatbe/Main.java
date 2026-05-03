package ch.hatbe;

import ch.hatbe.cli.CliArguments;
import ch.hatbe.protocol.ActionRegistry;
import ch.hatbe.protocol.actions.DisconnectAction;
import ch.hatbe.protocol.actions.auth.LoginAction;
import ch.hatbe.protocol.actions.auth.WhoAmIAction;
import ch.hatbe.server.ChatServer;
import ch.hatbe.server.ServerTerminal;
import ch.hatbe.server.client.ClientService;

import java.util.Scanner;

public class Main {
    static void main(String[] appArguments) {
        Main.registerArgs();

        if (!CliArguments.getInstance().parse(appArguments)) {
            return;
        }

        Main.registerActions();

        var server = Main.createServer();

        Main.createTerminal(server);

        server.stop(); // stop in the end, when server is finished (safety guard to free up resources)
    }

    private static ChatServer createServer() {

        ChatServer server = new ChatServer(CliArguments.getInstance().getInt("port", 12345));
        Thread serverThread = new Thread(server, "chat-server");
        serverThread.start();
        return server;
    }

    private static void createTerminal(ChatServer server) {
        Thread consoleThread = new Thread(new ServerTerminal(server), "server-terminal");
        consoleThread.start();
    }

    private static void registerArgs() {
        var cli = CliArguments.getInstance();

        cli.registerOption("p", "port", "The TCP port used to start the chatserver", false);
    }

    private static void registerActions() {
        var ar = ActionRegistry.getInstance();

        ar.registerAction("DISCONNECT", DisconnectAction.class);
        ar.registerAction("LOGIN", LoginAction.class);
        ar.registerAction("WHOAMI", WhoAmIAction.class);
    }
}
