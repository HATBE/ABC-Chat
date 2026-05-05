package ch.hatbe.protocol.actions;

import ch.hatbe.protocol.responses.AckResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisconnectAction extends Action {
    @Override
    public void handle(ClientSession session) {
        session.send(new AckResponse());
        session.disconnect();
    }
}
