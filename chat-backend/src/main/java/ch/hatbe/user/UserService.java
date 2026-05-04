package ch.hatbe.user;

import ch.hatbe.server.ServerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {
    public User login(String username, ServerContext context) throws Exception {
        this.validateUsername(username);

        username = username.strip();

        if (context.getClientManager().isUsernameInUse(username)) {
            throw new Exception("Username is currently in use");
        }

        return new User(username);
    }

    private void validateUsername(String username) throws Exception {
        if (username == null || username.isBlank()) {
            throw new Exception("Username is required");
        }

        if (username.length() < 3 || username.length() > 16) {
            throw new Exception("The length of the Username must be between 3 and 16 characters");
        }
    }
}
