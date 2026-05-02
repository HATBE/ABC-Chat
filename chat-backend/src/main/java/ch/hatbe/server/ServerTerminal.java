package ch.hatbe.server;

import java.util.Scanner;

public class ServerTerminal implements Runnable {
    private final ChatServer server;

    public ServerTerminal(ChatServer server) {
        this.server = server;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Server console ready.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("status")) {
                printStatus();
                return;
            } else {
                System.out.println("Unknown command: " + input);
            }
        }
    }

    private void printStatus() {
        System.out.println("Server running: " + server.isRunning());
    }
}
