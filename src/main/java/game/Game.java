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
        RoundResult result = new RoundResult(players);
        Collection<Player> winners = result.winners();
        Collection<Player> loosers = result.loosers();
        visitor.roundComplete(result);
        resetRound();
        result.mayRewardPlayers();
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
