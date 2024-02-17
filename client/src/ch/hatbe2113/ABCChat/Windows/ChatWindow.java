package ch.hatbe2113.ABCChat.Windows;

import ch.hatbe2113.ABCChat.Application;

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

        this.infoText.setText("Hello\nnewline");
        this.infoText.setFocusable(false);
        this.infoText.setEditable(false);

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
