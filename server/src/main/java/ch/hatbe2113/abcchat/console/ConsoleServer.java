package ch.hatbe2113.abcchat.console;

import ch.hatbe2113.abcchat.server.Server;

import java.util.Scanner;

public class ConsoleServer implements Runnable {
    private Server server;
    private Thread thread;

    public ConsoleServer(Server server) {
        this.server = server;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (this.server.isRunning()) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();

            if ("status".equalsIgnoreCase(input)) {
                System.out.println("Server status: Running");
            } else if ("stop".equalsIgnoreCase(input)) {
                System.out.println("Stopping server...");
                this.server.stop();
            } else {
                System.out.println("Unknown command: " + input);
            }
        }
    }
}
