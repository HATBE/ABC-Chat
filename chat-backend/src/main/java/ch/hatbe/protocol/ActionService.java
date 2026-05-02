package ch.hatbe.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionService {
    private final ObjectMapper mapper = new ObjectMapper();




    /*public void route(String json, ClientSession session) {
        try {
            JsonNode node = mapper.readTree(json);

            String type = node.get("type").asText();



            if (null == null) {
                session.send("{\"error\":\"Unknown typü\"}"); // TODO: SEND ERROR PACKAGE
                return;
            }

            Object packet = mapper.treeToValue(node, type));

            //handler.handle(packet, session);
        } catch (Exception e) {
            session.send("{\"error\":\"Invalid package\"}"); // TODO: SEND ERROR PACKAGE
        }
    }*/
}
