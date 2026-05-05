package ch.hatbe.chat;

import ch.hatbe.user.User;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatManager {
    @Getter
    private final List<Chat> chats = new CopyOnWriteArrayList<>();

    public Chat create(User creater, String name) throws Exception {
        if (this.chats.stream().filter( c -> c.getName().equals(name)).findFirst().orElse(null) != null) {
            throw new Exception("Chat does already exist");
        }

        Chat chat = new Chat(name, creater);
        this.chats.add(chat);

        return chat;
    }

    public Chat join(String name, User user) throws Exception {
       Chat chat = this.chats.stream().filter( c -> c.getName().equals(name)).findFirst().orElse(null);

       if (chat == null) {
           throw new Exception("Chat does not exist");
       }

        this.leaveAllChats(user);

       chat.addUser(user);

       return chat;
    }

    public void leaveAllChats(User user) {
        List<Chat> chatsOfUSer = this.getChatsOfUser(user);

        for(Chat chatOfUser : chatsOfUSer) {
            chatOfUser.removeUser(user);
        }
    }

    public void leave(User user) throws Exception {
        Chat chat = this.getCurrentChat(user);

        if (chat == null) {
            throw new Exception("Chat does not exist");
        }

        chat.removeUser(user);
    }

    public List<Chat> getChatsOfUser(User user) {
        return this.chats.stream().filter(c -> c.getUsers().contains(user)).toList();
    }

    public Chat getCurrentChat(User user) {
        return this.chats.stream().filter(c -> c.getUsers().contains(user)).findFirst().orElse(null);
    }
}
