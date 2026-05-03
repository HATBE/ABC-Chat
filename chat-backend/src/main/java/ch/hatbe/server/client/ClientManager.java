package ch.hatbe.server.client;

import ch.hatbe.protocol.responses.Response;

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
        String json = response.toJson();

        for (ClientSession session : this.sessions) {
            session.send(json);
        }
    }

    public int count() {
        return this.sessions.size();
    }
}
