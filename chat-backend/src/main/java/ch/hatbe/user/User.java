package ch.hatbe.user;

import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "session")
public class User {
    @Getter
    private String username;

    @Getter
    @JsonIgnore
    private ClientSession session;

    public User(String username , ClientSession session) {
        this.username = username;
        this.session = session;
    }
}
