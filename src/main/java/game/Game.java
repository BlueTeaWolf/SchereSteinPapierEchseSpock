package game;

import java.util.*;

public class Game {
    private final int maxRounds;
    private int round;
    private GameVisitor visitor;
    private final Player[] players;
    private int drafter;

    public Game(int maxRounds, PlayerConfiguration playerConfiguration) {
        this.maxRounds = maxRounds;
        players = playerConfiguration.build();
    }

    public Player[] players() {
        return players;
    }

    public void advanceDraft(Auswahl auswahl) {
        Player current = players[drafter];
        current.setAuswahl(auswahl);
        visitor.draw(current);
        drafter++;
        if (drafter >= players.length) {
            pushMatchResult();
        } else if (players[drafter].getType() == PlayerType.AI) {
            int zufallsZahl = (int) (Math.random() * 5);
            advanceDraft(Auswahl.values()[zufallsZahl]);
        }
    }

    private void resetRound() {
        drafter = 0;
        for (Player player : players) {
            player.setAuswahl(null);
        }
    }

    public void setVisitor(GameVisitor visitor) {
        this.visitor = visitor;
    }

    public void pushMatchResult() {
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
            System.out.println(player.displayName() + " has score " + score);
            winMap.put(player, score);
        }
        Collection<Player> winners = new ArrayList<>();
        Collection<Player> loosers = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : winMap.entrySet()) {
            (entry.getValue() == heighestScore ? winners : loosers).add(entry.getKey());
        }
        visitor.roundComplete(winners, loosers);
        resetRound();
        if (!loosers.isEmpty()) {
            for (Player winner : winners) {
                winner.increaseScore();
            }
        }
        for (Player player : players) {
            visitor.updateScore(player);
        }

        if (++round > maxRounds) {
            Player winner = currentWinner();
            if (winner != null) {
                visitor.end(winner);
            }
        }
    }

    private Player currentWinner() {
        Player winner = Arrays.stream(players)
            .max(Comparator.comparingInt(Player::getScore))
            .orElseThrow(IllegalStateException::new); /* unreachable */
       if (checkForMultipleWinners(winner)) {
           return null;
       }
        return winner;
    }

    private boolean checkForMultipleWinners(Player oneWinner) {
        int winners = 0;    
        for (Player player : players) {
            if (player.getScore() == oneWinner.getScore() && winners++ == 1) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        for (Player player : players) {
            visitor.updateScore(player);
        }
    }
}
