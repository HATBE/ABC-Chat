package ch.hatbe.server;

import ch.hatbe.chat.ChatManager;
import ch.hatbe.server.client.ClientManager;
import lombok.Getter;

public class ServerContext {
    @Getter
    private final ClientManager clientManager;

    @Getter
    private final ChatManager chatManager;

    public ServerContext(ClientManager clientManager, ChatManager chatManager) {
        this.clientManager = clientManager;
        this.chatManager = chatManager;
    }
}
