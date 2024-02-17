package ch.hatbe2113.ABCChat.client.Windows;

import ch.hatbe2113.ABCChat.client.Application;

import javax.swing.*;

public class ChatWindow extends Window {
    private Application app;
    private JPanel mainPanel;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;
    private JTextPane infoText;

    public ChatWindow(Application app) {
        super();

        this.app = app;

        initFrame();
        listeners();
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
        sendButton.addActionListener(e -> addMessageToChatArea());

        messageField.addActionListener(e -> addMessageToChatArea());
    }

    private void addMessageToChatArea() {
        chatArea.append(messageField.getText() + "\n");
        messageField.setText("");
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
}
