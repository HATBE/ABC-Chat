package ch.hatbe.protocol.actions;

import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.server.client.ClientSession;

public abstract class Action {
    private String type;

    public abstract void handle(ClientSession session);

    protected void error(ClientSession session, String message) {
        session.send(new ErrorResponse(message));
    }
}
