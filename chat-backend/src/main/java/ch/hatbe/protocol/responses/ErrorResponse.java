package ch.hatbe.protocol.responses;

public class ErrorResponse extends Response {
    public String message;

    public ErrorResponse(String message) {
        super(false);
        this.message = message;
    }

    @Override
    public String getType() {
        return "ERROR";
    }
}
