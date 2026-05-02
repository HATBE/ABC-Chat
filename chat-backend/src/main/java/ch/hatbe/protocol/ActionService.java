package ch.hatbe.protocol;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActionService {
    private final ObjectMapper mapper;
    private final ClientSession session;

    public ActionService(ClientSession session) {
        this.mapper = new ObjectMapper();
        this.session = session;
    }

    public void route(String json) {
        try {
            JsonNode node = mapper.readTree(json);

            JsonNode typeNode = node.get("type");
            if (typeNode == null || !typeNode.isTextual()) {
                session.send(new ErrorResponse("Missing or invalid action type").toJson());
                return;
            }

            String type = typeNode.asText();

            var actionClass = ActionRegistry.getInstance().getAction(type);

            if (actionClass == null) {
                session.send(new ErrorResponse("Action does not exist!").toJson());
                return;
            }

            Action action = mapper.treeToValue(node, actionClass);

            action.handle(this.session);
        } catch (JsonProcessingException e) {
            session.send(new ErrorResponse("Action does not exist!").toJson());
        }
    }
}
