package ch.hatbe2113.ABCChat.client;

import ch.hatbe2113.ABCChat.client.Windows.WindowManager;
import ch.hatbe2113.ABCChat.client.Windows.WindowNames;

public class Application {
    private WindowManager windowManager;

    public Application() {
        this.windowManager = new WindowManager(this);

        windowManager.switchWindow(WindowNames.LOGIN);
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }
}
