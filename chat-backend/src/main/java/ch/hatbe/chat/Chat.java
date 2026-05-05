package ch.hatbe.chat;

import ch.hatbe.user.User;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat {
    @Getter
    private final List<User> users = new CopyOnWriteArrayList<>();

    @Getter
    private final String name;
    private final User creater;

    public Chat(String name, User creater) {
        this.name = name;
        this.creater = creater;
        this.addUser(creater);
    }

    public void addUser(User user) {
        if (this.users.contains(user)) {
            return;
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        if (user == this.creater) {
            return;
        }
        this.removeUser(user);
    }
}
