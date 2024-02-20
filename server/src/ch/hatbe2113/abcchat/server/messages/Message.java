package ch.hatbe2113.abcchat.server.messages;

public abstract class Message {
    protected String name;
    protected String spacer = "|";

    public Message() {}

    public abstract String serialize();
    public String ack() {
        return String.format(this.name + this.spacer + "ACK");
    }

}
