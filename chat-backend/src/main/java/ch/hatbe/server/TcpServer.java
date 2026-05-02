package ch.hatbe.server;

import ch.hatbe.server.client.ClientService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class TcpServer {
    private int port;

    @Getter
    private ServerSocket serverSocket;

    private boolean isRunning = false;
    private ClientService clientService;

    public TcpServer(int port) {
        this.port = port;
    }

    public void start() {
        if(this.isRunning) {
            return;
        }

        try {
            this.serverSocket = new ServerSocket(this.port);
            this.isRunning = true;

            this.clientService = new ClientService(this);

            log.info("The server successfully started on port {}.", this.port);


            this.clientService.waitForClients();
        } catch(IOException e) {
            this.isRunning = false;
            log.error("Could not start the chat server!", e);
        }
    }

    public void stop() {
        this.isRunning = false;

        if (this.clientService != null) {
            this.clientService.disconnectAllClients();
        }

        if (this.serverSocket == null || this.serverSocket.isClosed()) {
            return;
        }

        try {
            this.serverSocket.close();
            log.info("The server successfully stopped.");
        } catch(IOException e) {
            log.error("Could not stop server!", e);
        }
    }

    public boolean isRunning() {
        return this.isRunning && this.serverSocket != null && !this.serverSocket.isClosed();
    }
}
