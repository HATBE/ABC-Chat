package ch.hatbe2113.abcchat.server.networkClient;

import ch.hatbe2113.abcchat.server.Application;
import ch.hatbe2113.abcchat.server.networkPackage.PackageHandler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkClientThread implements Runnable {
    private Application app;
    private Thread thread;
    private Socket connectionToClient;
    private NetworkClient client;
    private BufferedReader fromClientReader;
    private PrintWriter toClientWriter;

    public NetworkClientThread(Application app, NetworkClient client, Socket connection) {
        this.app = app;
        this.client = client;
        this.connectionToClient = connection;

        this.thread = new Thread(this);
        this.thread.start(); // start new client thread
    }

    public void sendString(String message) {
        this.toClientWriter.println(message);
    }

    @Override
    public void run() {
        try {
            this.fromClientReader = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream(), StandardCharsets.UTF_8));
            this.toClientWriter = new PrintWriter(new OutputStreamWriter(connectionToClient.getOutputStream(), StandardCharsets.UTF_8), true);

            // TODO: handle join, client joined here (not user, client)

            this.client.sendString("CONN|ACK"); // acknowledge the connection to the client

            while(!this.app.getServer().getServerSocket().isClosed() && !this.connectionToClient.isClosed()) {
                System.out.println("thread: " + Thread.currentThread().getId());
                System.out.println(this.app.getClientManager().getClients().size());
                String data = fromClientReader.readLine();

                if(data == null) {
                    // client disconnects, goto finally
                    break;
                }

                if(data.isEmpty()) {
                    continue;
                }

                new PackageHandler(this.app, this.client).handlePackage(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.app.getClientManager().getClients().size());
            this.disconnect();
        }
    }

    public PrintWriter getToClientWriter() {
        return toClientWriter;
    }

    public BufferedReader getFromClientReader() {
        return fromClientReader;
    }

    public void disconnect() {
        this.app.getClientManager().removeClient(this.client);

        System.out.printf("Client disconnected: %s.\n", this.client.getIpAddress());

        if(this.fromClientReader != null) {
            try {
                this.fromClientReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(this.toClientWriter != null) {
            this.toClientWriter.close();
        }

        if(this.connectionToClient != null) {
            try {
                this.connectionToClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}