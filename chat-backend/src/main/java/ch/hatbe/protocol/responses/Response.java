package ch.hatbe.protocol.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonPropertyOrder({ "type", "success" })
public abstract class Response {
    public boolean success;

    public Response(boolean success) {
        this.success = success;
    }

    @JsonProperty("type")
    public abstract String getType();

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
