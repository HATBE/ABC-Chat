package ch.hatbe.protocol;

import ch.hatbe.protocol.actions.Action;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionRegistry {
    private static ActionRegistry INSTANCE;

    private final Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    private ActionRegistry() {}

    public static ActionRegistry getInstance() {
        if (ActionRegistry.INSTANCE == null) {
            ActionRegistry.INSTANCE = new ActionRegistry();
        }
        return ActionRegistry.INSTANCE;
    }

    public void registerAction(String name, Class<? extends Action> action) {
        this.actions.put(name.strip().toUpperCase(), action);
    }

    public Class<? extends Action> getAction(String name) {
        return this.actions.get(name);
    }
}
