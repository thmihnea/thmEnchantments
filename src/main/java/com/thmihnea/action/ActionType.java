package com.thmihnea.action;

import java.util.Arrays;
import java.util.Optional;

public enum ActionType {
    STAT_TRAK_PLAYER("statTrak(player)", new StatTrakPlayer());

    private String configField;
    private Action action;

    ActionType(String configField, Action action) {
        this.configField = configField;
        this.action = action;
    }

    public String getConfigField() {
        return this.configField;
    }

    public Action getAction() {
        return this.action;
    }

    public static boolean exists(String configField) {
        Optional<ActionType> match = Arrays.stream(values()).filter(obj -> obj.getConfigField().equalsIgnoreCase(configField)).findFirst();
        return match.isPresent();
    }

    public static ActionType getFromConfigField(String configField) {
        Optional<ActionType> match = Arrays.stream(values()).filter(obj -> obj.getConfigField().equalsIgnoreCase(configField)).findFirst();
        return match.orElse(null);
    }
}
