package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoundResult {
    private final Collection<Player> winners = new ArrayList<>();
    private final Collection<Player> loosers = new ArrayList<>();

    public RoundResult(Player[] players) {
        Map<Player, Integer> winMap = new HashMap<>();
        int heighestScore = Integer.MIN_VALUE;
        for (Player player : players) {
            int score = 0;
            for (Player opponent : players) {
                switch (player.getAuswahl().matchResult(opponent.getAuswahl())) {
                    case Verloren -> score--;
                    case Gewonnen -> score++;
                }
            }
            if (score > heighestScore) {
                heighestScore = score;
            }
            winMap.put(player, score);
        }

        for (Map.Entry<Player, Integer> entry : winMap.entrySet()) {
            (entry.getValue() == heighestScore ? winners : loosers).add(entry.getKey());
        }
    }

    public Collection<Player> winners() {
        return winners;
    }

    public Collection<Player> loosers() {
        return loosers;
    }

    public void mayRewardPlayers() {
        if (!loosers.isEmpty()) {
            for (Player winner : winners) {
                winner.increaseScore();
            }
        }
    }
}
