package ch.hatbe2113.ABCChat.Windows;

import ch.hatbe2113.ABCChat.Application;

import javax.swing.*;

public class LoginWindow extends Window {
    private Application app;
    private JTextField usernameField;
    private JButton loginBtn;
    private JComboBox chatSelect;
    private JPanel mainPanel;

    public LoginWindow(Application app) {
        super();

        this.app = app;

        initFrame();
        listeners();
    }

    private void initFrame() {
        this.frame.setTitle("ABC-Chat - Login");
        this.frame.setSize(300, 170);

        this.frame.setContentPane(mainPanel);

        this.chatSelect.addItem("Chat1");
        this.chatSelect.addItem("Chat2");
        this.chatSelect.addItem("Chat3");

        this.show();
    }

    private void listeners() {
        loginBtn.addActionListener(e -> {
            System.out.println(this.usernameField.getText());
            System.out.println(this.chatSelect.getSelectedItem());

            app.getWindowManager().switchWindow(WindowNames.CHAT);
        });
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
