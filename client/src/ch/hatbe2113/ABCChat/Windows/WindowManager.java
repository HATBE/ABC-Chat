package ch.hatbe2113.ABCChat.Windows;

import ch.hatbe2113.ABCChat.Application;

public class WindowManager {
    private Application app;
    private Window currentWindow;
    public WindowManager(Application app) {
        this.app = app;
    }

    public void killCurrentWindow() {
        currentWindow.kill();
    }

    public void switchWindow(WindowNames windowName) {
        if(this.currentWindow != null) {
            this.killCurrentWindow();
        }

        Window newWindow;

        if (windowName == WindowNames.LOGIN) {
            newWindow = new LoginWindow(this.app);
        } else if (windowName == WindowNames.CHAT) {
            newWindow = new ChatWindow(this.app);
        } else {
            return;
        }

        this.currentWindow = newWindow;
    }

    public Window getCurrentWindow() {
        return currentWindow;
    }
}
