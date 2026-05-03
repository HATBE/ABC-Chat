package ch.hatbe.server.client.entities;

import ch.hatbe.user.User;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

public class Client {
    @Getter
    private final Socket connection;

    @Getter
    @Setter
    private User user = null;

    public Client(Socket connection) {
        this.connection = connection;
    }
}
