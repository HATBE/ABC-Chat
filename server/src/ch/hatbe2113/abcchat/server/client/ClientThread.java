package ch.hatbe2113.abcchat.server.client;

import ch.hatbe2113.abcchat.server.Application;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Application app;
    private Socket connection;
    private Client client;
    private BufferedReader fromClientReader;
    private PrintWriter toClientWriter;

    public ClientThread(Application app, Client client, Socket connection) {
        this.app = app;
        this.client = client;
        this.connection = connection;

        new Thread(this).start(); // Start new Client Thread
    }

    public void sendMessage(String message) {
        this.toClientWriter.println(message);
    }

    @Override
    public void run() {
        try {
            this.fromClientReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            this.toClientWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"), true);

            this.sendJoinMessage();

            String message = fromClientReader.readLine();
            while(message != null) {
                System.out.println(message);
                this.app.getClientManager().broadcastMessage(String.format("%s: %s", this.client.getIpAddress(), message));
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
        this.app.getClientManager().broadcastMessage(client.getIpAddress() + " joined the Chat.");
        this.client.sendMessage("Welcome to Chat1");
    }

    private void sendDisconnectMessage() {
        System.out.printf("%s disconnected\n", this.client.getIpAddress());
        this.app.getClientManager().broadcastMessage(client.getIpAddress() + " left the Chat.");
    }
}
