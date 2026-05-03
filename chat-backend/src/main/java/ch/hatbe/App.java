package ch.hatbe;

import ch.hatbe.cli.CliArguments;
import ch.hatbe.protocol.ActionRegistry;
import ch.hatbe.protocol.ActionRouter;
import ch.hatbe.protocol.actions.DisconnectAction;
import ch.hatbe.protocol.actions.auth.LoginAction;
import ch.hatbe.protocol.actions.auth.LogoutAction;
import ch.hatbe.protocol.actions.auth.WhoAmIAction;
import ch.hatbe.server.ChatServer;
import ch.hatbe.server.ServerTerminal;

public class App {
    private final CliArguments cliArguments;
    private final ActionRegistry actionRegistry = new ActionRegistry();
    private final ActionRouter actionRouter;

    public App(String [] args) {
        this.cliArguments = new CliArguments(args);
        this.actionRouter = new ActionRouter(this.actionRegistry);
    }

    public void start() {
        this.registerArgs();

        if (!this.cliArguments.parse()) {
            return;
        }

        this.registerActions();

        var server = this.createServer();

        this.createTerminal(server);
    }

    private ChatServer createServer() {
        ChatServer server = new ChatServer(
                this.cliArguments.getInt("port", 12345),
                actionRouter
        );

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop)); // when server stops, call server.stop

        Thread serverThread = new Thread(server, "chat-server");
        serverThread.start();

        return server;
    }

    private void createTerminal(ChatServer server) {
        Thread consoleThread = new Thread(new ServerTerminal(server), "server-terminal");
        consoleThread.start();
    }

    private void registerArgs() {
       this.cliArguments
               .registerOption("p", "port", "The TCP port used to start the chatserver", false);
    }

    private void registerActions() {
        this.actionRegistry
                .registerAction("DISCONNECT", DisconnectAction.class)
                .registerAction("LOGIN", LoginAction.class)
                .registerAction("WHOAMI", WhoAmIAction.class)
                .registerAction("LOGOUT", LogoutAction.class);
    }
}
