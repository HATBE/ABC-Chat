package ch.hatbe.chat;

import ch.hatbe.protocol.responses.*;
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
        this.sendJoinMessage(user);
    }

    public void removeUser(User user) {
        if (user == this.creater) {
            return;
        }
        this.removeUser(user);
        this.sendLeaveMessage(user);
    }

    private void sendJoinMessage(User sender) {
        for (User user : this.users) {
            if (user.getUsername().equals(sender.getUsername())) {
                continue;
            }
            user.getSession().send(new JoinResponse(user));
        }
    }

    private void sendLeaveMessage(User sender) {
        for (User user : this.users) {
            if (user.getUsername().equals(sender.getUsername())) {
                continue;
            }
            user.getSession().send(new LeaveResponse(user));
        }
    }

    public void sendMessage(String message, User sender) {
        for (User user : this.users) {
            if (user.getUsername().equals(sender.getUsername())) {
               continue;
            }

            user.getSession().send(new ChatMessageResponse(sender, message));
        }
    }
}
