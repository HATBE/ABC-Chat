package ch.hatbe.user;

import lombok.Getter;

@Getter
public class User {
    @Getter
    private String username;

    public User(String username) {
        this.username = username;
    }
}
