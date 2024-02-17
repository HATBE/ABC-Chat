package ch.hatbe2113.ABCChat.client.Windows;

import ch.hatbe2113.ABCChat.client.Application;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ChatWindow extends Window {
    private String address;
    private int port;
    private Socket serverConnection;
    private BufferedReader fromServerReader;
    private PrintWriter toServerWriter;

    private Application app;
    private JPanel mainPanel;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;
    private JTextPane infoText;
    private JScrollPane chatScroll;

    public ChatWindow(Application app) {
        super();

        this.app = app;

        this.address = "localhost";
        this.port = 1111;

        initFrame();
        listeners();

        this.receiveMessages();
    }

    private void initFrame() {
        this.frame.setTitle("ABC-Chat");
        this.frame.setSize(800, 500);

        this.frame.setContentPane(mainPanel);

        renderChatInfo();

        this.messageField.requestFocus();

        this.show();
    }

    private void listeners() {
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String message = this.messageField.getText();

        if(!message.isEmpty()) {
            this.toServerWriter.println(message);
            this.toServerWriter.flush();
            this.messageField.setText("");
        }
    }

    private void renderChatInfo() {
        String chatInfo = String.format("Chat: %s\n", "Chat1");
        chatInfo += String.format("Username: %s", "Hanspeter");

        this.infoText.setText(chatInfo);

        this.infoText.setFocusable(false);
        this.infoText.setEditable(false);
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public void hide() {
        this.frame.setVisible(false);
    }

    public void kill() {
        this.frame.dispose();
    }

    private void receiveMessages() {
        try {
            this.serverConnection = new Socket(this.address, this.port);

            this.fromServerReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            this.toServerWriter = new PrintWriter(new OutputStreamWriter(serverConnection.getOutputStream()));

            while(true) {
                String message = fromServerReader.readLine();
                chatArea.append(message + "\n");
                chatScroll.getVerticalScrollBar().setValue(chatScroll.getVerticalScrollBar().getMaximum());
            }
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

            if(fromServerReader != null) {
                try {
                    fromServerReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            toServerWriter.close();
        }
    }
}
