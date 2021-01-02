package com.thmihnea.action;

import com.thmihnea.action.actions.Earthquake;
import com.thmihnea.action.actions.StatTrakPlayer;
import com.thmihnea.action.actions.ThreeShot;
import com.thmihnea.action.actions.Wings;

import java.util.Arrays;
import java.util.Optional;

public enum ActionType {
    STAT_TRAK_PLAYER("statTrak(player)", new StatTrakPlayer()),
    TRIPLE_SHOT("tripleShot", new ThreeShot()),
    EARTHQUAKE("earthquake", new Earthquake()),
    WINGS("fullSetFly", new Wings());

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
