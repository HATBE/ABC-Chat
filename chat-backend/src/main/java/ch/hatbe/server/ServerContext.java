package ch.hatbe.server;

import ch.hatbe.chat.ChatManager;
import ch.hatbe.server.client.ClientManager;
import ch.hatbe.user.UserService;
import lombok.Getter;

public class ServerContext {
    @Getter
    private final ClientManager clientManager;

    @Getter
    private final ChatManager chatManager;

    @Getter
    private final UserService userService;

    public ServerContext(ClientManager clientManager, ChatManager chatManager, UserService userService) {
        this.clientManager = clientManager;
        this.chatManager = chatManager;
        this.userService = userService;
    }
}
