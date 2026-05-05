package ch.hatbe.server.client;

import ch.hatbe.chat.ChatManager;
import ch.hatbe.protocol.ActionRouter;
import ch.hatbe.protocol.responses.Response;
import ch.hatbe.protocol.responses.WelcomeResponse;
import ch.hatbe.server.ServerContext;
import ch.hatbe.server.client.entities.Client;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Slf4j
public class ClientSession implements Runnable {
    private final ActionRouter actionRouter;
    private final Consumer<ClientSession> onDisconnect;

    @Getter
    private final ServerContext context;

    private BufferedReader reader;
    private PrintWriter writer;

    private boolean isRunning = true;

    @Getter
    private final Client client;

    public ClientSession(Socket connection, ActionRouter actionRouter, Consumer<ClientSession> onDisconnect, ServerContext context) {
        this.client = new Client(connection);
        this.actionRouter = actionRouter;
        this.onDisconnect = onDisconnect;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.getClient().getConnection().getInputStream(), StandardCharsets.UTF_8));
            this.writer = new PrintWriter(new OutputStreamWriter(this.getClient().getConnection().getOutputStream(), StandardCharsets.UTF_8), true);

            this.send(new WelcomeResponse());

            String message;

            while (this.isRunning && (message = this.reader.readLine()) != null) {
                if(message.isBlank()) {
                    continue;
                }
                this.actionRouter.route(message, this);
            }
        } catch (IOException e) {
            log.warn("Client connection lost.", e);
        } finally {
            this.disconnect();
        }
    }

    public void send(Response response) {
        if (this.writer != null) {
            this.writer.println(response.toJson());
        }
    }

    public void disconnect() {
        this.isRunning = false;

        try {
            if (this.reader != null) {
                this.reader.close();
            }

            if (this.writer != null) {
                this.writer.close();
            }

            if (!this.getClient().getConnection().isClosed()) {
                this.getClient().getConnection().close();
            }
        } catch (IOException e) {
            log.warn("Could not close connection to client cleanly.", e);
        } finally {
            this.onDisconnect.accept(this);
        }
    }
}
