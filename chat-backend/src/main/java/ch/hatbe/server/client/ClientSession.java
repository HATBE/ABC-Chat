package ch.hatbe.server.client;

import ch.hatbe.protocol.ActionService;
import ch.hatbe.server.client.entities.Client;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientSession implements Runnable {
    private final ClientService clientService;
    private final ActionService packageService;

    private BufferedReader reader;
    private PrintWriter writer;

    private boolean isRunning = true;

    @Getter
    private final Client client;

    public ClientSession(Socket connection, ClientService clientService, ActionService packageService) {
        this.client = new Client(connection);
        this.clientService = clientService;
        this.packageService = packageService;
    }

    @Override
    public void run() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.getClient().getConnection().getInputStream(), StandardCharsets.UTF_8));
            this.writer = new PrintWriter(new OutputStreamWriter(this.getClient().getConnection().getOutputStream(), StandardCharsets.UTF_8), true);

            this.send("Welcome to the chat!"); // TODO: send welcome package

            String message;

            while (this.isRunning && (message = this.reader.readLine()) != null) {
                // TODO: handler
            }
        } catch (IOException e) {
            log.warn("Client connection lost.", e);
        } finally {
            this.disconnect();
            this.clientService.disconnectClient(this);
        }
    }

    public void send(String message) {
        if (this.writer != null) {
            this.writer.println(message);
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

            if (this.getClient().getConnection() != null && !this.getClient().getConnection().isClosed()) {
                this.getClient().getConnection().close();
            }
            this.clientService.disconnectClient(this);
        } catch (IOException e) {
            log.warn("Could not close connection to client cleanly.", e);
        }
    }
}
