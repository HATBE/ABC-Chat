package ch.hatbe.protocol;

import ch.hatbe.protocol.actions.Action;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionRegistry {
    private final Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    public ActionRegistry registerAction(String name, Class<? extends Action> action) {
        this.actions.put(name.strip().toUpperCase(), action);
        return this;
    }

    public Class<? extends Action> getAction(String name) {
        return this.actions.get(name.strip().toUpperCase());
    }
}
