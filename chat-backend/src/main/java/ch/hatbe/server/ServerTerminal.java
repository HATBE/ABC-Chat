package ch.hatbe.server;

import ch.hatbe.server.client.entities.Client;

import java.util.List;
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

            switch (input) {
                case "status" -> this.printStatus();
                case "listclients" -> this.listClients();
                case "disconnectall" -> this.disconnectAllClients();
                default -> System.out.println("Unknown command: " + input);
            }
        }
    }

    private void listClients() {
        List<Client> clients = this.server.getContext().getClientManager().getClients();

        if (clients.isEmpty()) {
            System.out.println("There are no clients connected");
            return;
        }

        System.out.println(String.format("Clients (%s): ", clients.size()));
        for (Client client : clients) {
            System.out.println(String.format("%s - %s", client.getConnection().getInetAddress(), client.getUser() != null ? client.getUser().getUsername() : "Not logged in"));
        }

    }

    private void disconnectAllClients() {
        this.server.getContext().getClientManager().disconnectAll();
        System.out.println("Disconnected all Clients");
    }

    private void printStatus() {
        System.out.println("Server running: " + server.isRunning());
    }
}
