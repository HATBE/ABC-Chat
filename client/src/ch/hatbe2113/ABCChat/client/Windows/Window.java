package ch.hatbe2113.ABCChat.client.Windows;

import javax.swing.*;

public abstract class Window {
    protected JFrame frame;

    public Window() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
    }

    public abstract void show();
    public abstract void kill();
    public abstract void hide();
}
