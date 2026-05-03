package ch.hatbe.server;

import ch.hatbe.protocol.ActionRouter;
import ch.hatbe.server.client.ClientManager;
import ch.hatbe.server.client.ClientSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ChatServer implements Runnable {
    private final int port;

    private final ActionRouter actionRouter;
    private final ClientManager clientManager = new ClientManager();
    private final ExecutorService clientExecutor = Executors.newCachedThreadPool();

    private ServerSocket serverSocket;

    private volatile boolean isRunning = false;

    public ChatServer(int port, ActionRouter actionRouter) {
        this.port = port;
        this.actionRouter = actionRouter;
    }

    @Override
    public void run() {
        this.start();
    }

    public void start() {
        if(this.isRunning) {
            return;
        }

        try (ServerSocket socket = new ServerSocket(port)) {
            this.serverSocket = socket;
            this.isRunning = true;

            log.info("The server successfully started on port {}.", this.port);

            while (this.isRunning()) {
                Socket clientSocket = socket.accept();

                ClientSession session = new ClientSession(
                        clientSocket,
                        actionRouter,
                        clientManager::remove
                );

                clientManager.add(session);
                clientExecutor.submit(session);
            }
        } catch(IOException e) {
            log.error("Could not start the chat server!", e);
        } finally {
            this.stop();
        }
    }

    public void stop() {
        if (!this.isRunning) {
            return;
        }

        this.isRunning = false;

        this.clientManager.disconnectAll();
        this.clientExecutor.shutdownNow();

        if (this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
                log.info("The server successfully stopped.");
            } catch(IOException e) {
                log.error("Could not stop server!", e);
            }
        }
    }

    public boolean isRunning() {
        return this.isRunning && this.serverSocket != null && !this.serverSocket.isClosed();
    }
}
