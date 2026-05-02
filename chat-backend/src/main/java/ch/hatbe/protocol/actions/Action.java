package ch.hatbe.protocol.actions;

import ch.hatbe.server.client.ClientSession;

public abstract class Action {
    private String type;

    public abstract void handle(ClientSession session);
}
