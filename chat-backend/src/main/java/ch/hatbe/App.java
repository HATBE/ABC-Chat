package ch.hatbe;

import ch.hatbe.chat.ChatManager;
import ch.hatbe.cli.CliArguments;
import ch.hatbe.protocol.ActionRegistry;
import ch.hatbe.protocol.ActionRouter;
import ch.hatbe.protocol.actions.DisconnectAction;
import ch.hatbe.protocol.actions.TestAction;
import ch.hatbe.protocol.actions.auth.LoginAction;
import ch.hatbe.protocol.actions.auth.LogoutAction;
import ch.hatbe.protocol.actions.auth.WhoAmIAction;
import ch.hatbe.protocol.actions.chat.CreateChatAction;
import ch.hatbe.protocol.actions.chat.JoinChatAction;
import ch.hatbe.protocol.actions.chat.LeaveChatAction;
import ch.hatbe.protocol.actions.chat.SendMessageAction;
import ch.hatbe.server.ChatServer;
import ch.hatbe.server.ServerContext;
import ch.hatbe.server.ServerTerminal;
import ch.hatbe.server.client.ClientManager;
import ch.hatbe.user.UserService;

public class App {
    private final CliArguments cliArguments;

    private final ActionRegistry actionRegistry = new ActionRegistry();
    private final ActionRouter actionRouter;
    private ServerContext context;

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

        this.buildContext();

        var server = this.createServer();

        this.createTerminal(server);
    }

    private void buildContext() {
        this.context = new ServerContext(
                new ClientManager(),
                new ChatManager(),
                new UserService()
        );
    }

    private ChatServer createServer() {
        ChatServer server = new ChatServer(
                this.cliArguments.getInt("port", 12345),
                actionRouter,
                context
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
                .registerAction("LOGOUT", LogoutAction.class)
                .registerAction("TEST", TestAction.class)
                .registerAction("CREATE_CHAT", CreateChatAction.class)
                .registerAction("SEND_MESSAGE", SendMessageAction.class)
                .registerAction("JOIN_CHAT", JoinChatAction.class)
                .registerAction("LEAVE_CHAT", LeaveChatAction.class);
    }
}
