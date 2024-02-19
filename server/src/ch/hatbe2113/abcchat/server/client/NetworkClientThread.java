package ch.hatbe2113.abcchat.server.client;

import ch.hatbe2113.abcchat.server.Application;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkClientThread implements Runnable {
    private Application app;
    private Socket connectionToClient;
    private NetworkClient client;
    private BufferedReader fromClientReader;
    private PrintWriter toClientWriter;

    public NetworkClientThread(Application app, NetworkClient client, Socket connection) {
        this.app = app;
        this.client = client;
        this.connectionToClient = connection;

        new Thread(this).start(); // Start new Client Thread
    }

    public void sendString(String message) {
        this.toClientWriter.println(message);
    }

    @Override
    public void run() {
        try {
            this.fromClientReader = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream(), StandardCharsets.UTF_8));
            this.toClientWriter = new PrintWriter(new OutputStreamWriter(connectionToClient.getOutputStream(), StandardCharsets.UTF_8), true);

            this.sendJoinMessage();

            String message = fromClientReader.readLine();
            while(message != null) {
                System.out.println(message);
                this.app.getClientManager().broadcastString(String.format("%s: %s", this.client.getIpAddress(), message));
                message = fromClientReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.app.getClientManager().removeClient(this.client);
            sendDisconnectMessage();

            if(this.fromClientReader != null) {
                try {
                    this.fromClientReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(this.toClientWriter != null) {
                    this.toClientWriter.close();
                }
            }
        }
    }

    private void sendJoinMessage() {
        this.app.getClientManager().broadcastString(client.getIpAddress() + " joined the Chat.");
        this.client.sendString("Welcome to Chat1");
    }

    private void sendDisconnectMessage() {
        System.out.printf("%s disconnected\n", this.client.getIpAddress());
        this.app.getClientManager().broadcastString(client.getIpAddress() + " left the Chat.");
    }
}