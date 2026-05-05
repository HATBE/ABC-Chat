package ch.hatbe.server.client;

import ch.hatbe.protocol.responses.Response;
import ch.hatbe.server.client.entities.Client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {
    private final List<ClientSession> sessions = new CopyOnWriteArrayList<>();

    public void add(ClientSession session) {
        this.sessions.add(session);
    }

    public void remove(ClientSession session) {
        this.sessions.remove(session);
    }

    public void disconnectAll() {
        for (ClientSession session : this.sessions) {
            session.disconnect();
        }
        sessions.clear();
    }

    public void broadcast(Response response) {
        for (ClientSession session : this.sessions) {
            session.send(response);
        }
    }

    public List<Client> getClients() {
        return this.sessions.stream().map(ClientSession::getClient).toList();
    }

    public boolean isUsernameInUse(String username) {
        for (ClientSession session : this.sessions) {
            if (session.getClient().getUser() != null && username.equals(session.getClient().getUser().getUsername())) {
                return true;
            }
        }

        return false;
    }

    public int count() {
        return this.sessions.size();
    }
}
