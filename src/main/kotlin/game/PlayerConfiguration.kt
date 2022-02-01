package game;

import java.util.EnumMap;
import java.util.Map;

public class PlayerConfiguration {
    private final Map<PlayerType, Integer> players = new EnumMap<PlayerType, Integer>(PlayerType.class);
    private int offset;
    private Player[] array;

    public PlayerConfiguration add(PlayerType type, int count) {
        players.put(type, count);
        return this;
    }

    private int total() {
        return players.values().stream().mapToInt(Integer::intValue).sum();
    }

    private void fillType(PlayerType type, int count) {
        for (int i = 0; i < count; i++) {
            array[offset++] = new Player(type, i);
        }
    }

    public Player[] build() {
        array = new Player[total()];
        players.forEach(this::fillType);
        return array;
    }
}
