package ch.hatbe.protocol;

import ch.hatbe.protocol.actions.Action;
import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionRouter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ActionRegistry registry;

    public ActionRouter(ActionRegistry registry) {
        this.registry = registry;
    }

    public void route(String json, ClientSession session) {
        try {
            JsonNode node = this.mapper.readTree(json);

            JsonNode typeNode = node.get("type");
            if (typeNode == null || !typeNode.isTextual()) {
                session.send(new ErrorResponse("Missing or invalid action type"));
                return;
            }

            String type = typeNode.asText();

            Class<? extends Action> actionClass = this.registry.getAction(type);
            if (actionClass == null) {
                session.send(new ErrorResponse(String.format("Unknown action: %s", type)));
                return;
            }

            Action action = mapper.treeToValue(node, actionClass);
            action.handle(session);
        } catch (JsonProcessingException e) {
            session.send(new ErrorResponse("Invalid JSON"));
        }
    }
}
