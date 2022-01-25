package game;

import java.util.Locale;

public enum PlayerType {
    HUMAN,
    AI;

    public String displayName() {
        String name = name();
        return Character.toUpperCase(name.charAt(0))
                + name.substring(1).toLowerCase(Locale.ROOT);
    }
}
