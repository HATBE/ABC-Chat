package ch.hatbe.protocol.actions;

import ch.hatbe.protocol.responses.ErrorResponse;
import ch.hatbe.server.client.ClientSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestAction extends Action{
    @Override
    public void handle(ClientSession session) {
        new Thread(() -> {
            int count = 100;

            while (count >= 0) {
                if (count <= 5 || count % 10 == 0) {
                    session.send(new ErrorResponse(String.valueOf(count)).toJson());
                }

                count--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "test-countdown").start();
    }
}
