package ch.hatbe.protocol.responses;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WelcomeResponse extends Response {
    private final ObjectMapper mapper = new ObjectMapper();

    public String toJson() {
        return "";
    }
}
