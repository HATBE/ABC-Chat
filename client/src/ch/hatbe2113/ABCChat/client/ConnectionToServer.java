package ch.hatbe2113.ABCChat.client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ConnectionToServer {
    Application app;

    String address;
    int port;

    private Socket serverConnection;
    private BufferedReader fromServerReader;
    private PrintWriter toServerWriter;

    public ConnectionToServer(Application app, String address, int port) {
        this.app = app;
        this.port = port;
        this.address = address;

        try {
            this.serverConnection = new Socket(this.address, this.port);

            this.fromServerReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            this.toServerWriter = new PrintWriter(new OutputStreamWriter(serverConnection.getOutputStream()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, String.format("Connection failed: %s", address));
            e.printStackTrace();
        } finally {
            if(serverConnection != null) {
                try {
                    serverConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                if(fromServerReader != null) {
                    fromServerReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(fromServerReader != null) {
                toServerWriter.close();
            }
        }
    }

    public void receiveMessages() {
        while(!this.serverConnection.isClosed()) {
            try {
                String message = this.fromServerReader.readLine();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendString(String string) {
        if(string.isEmpty()) {
            return;
        }

        this.toServerWriter.println(string);
        this.toServerWriter.flush();
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
