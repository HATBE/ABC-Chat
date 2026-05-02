package ch.hatbe.server.client;

import ch.hatbe.server.TcpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ClientService {
    private final List<ClientSession> clients = new CopyOnWriteArrayList<>();
    private final TcpServer server;

    public ClientService(TcpServer server) {
        this.server = server;
    }

    public void waitForClients() {
        while (this.server.isRunning()) {
            log.info("Waiting for new clients to connect...");

            try {
                Socket clientConnection = this.server.getServerSocket().accept();
                this.setupNewClient(clientConnection);
                log.info("Accepted new client with ip: {}. Current client count: {}", clientConnection.getInetAddress(), this.getClientsCount());
            } catch(IOException e) {
                log.warn("Something went wrong while the client was connecting!", e);
            }
        }
    }

    public int getClientsCount() {
        return this.clients.size();
    }

    private void setupNewClient(Socket connectionToClient) {
        ClientSession clientSession = new ClientSession(connectionToClient, this);

        this.clients.add(clientSession);

        Thread thread = new Thread(clientSession);
        thread.start();
    }

    public void disconnectClient(ClientSession client) {
        //client.disconnect(); // TODO make disconnect without delete from list
        this.clients.remove(client);
        log.info("Client removed. Current client count: {}", this.getClientsCount());
    }

    public void disconnectAllClients() {
        for(ClientSession client : this.clients) {
            client.disconnect();
        }
        this.clients.clear();
    }

    public void broadcast(String msg) { // TODO: send packages
        for (ClientSession client : this.clients) {
            client.send(msg);
        }
    }
}
