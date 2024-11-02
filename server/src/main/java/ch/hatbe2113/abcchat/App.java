package ch.hatbe2113.abcchat;

import ch.hatbe2113.abcchat.server.Server;


public class App {
    private Server server;

    public App() {
        this.server = new Server(1111);
        //new ConsoleServer(this.server);

        this.server.start();

        this.server.waitForClients();

        this.stop();
    }

    public void stop() {
        this.server.stop();
    }
}
