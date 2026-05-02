package ch.hatbe.protocol.responses;

public class AckResponse extends Response {
    public AckResponse() {
        super(true);
    }

    @Override
    public String getType() {
        return "ACKNOWLEDGE";
    }
}
