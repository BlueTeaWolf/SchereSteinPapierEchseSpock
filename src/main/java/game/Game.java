package game;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class Game {
    private final int maxRounds;
    private int round;
    private GameVisitor visitor;
    private final Player[] players = new Player[] {
            new Player(PlayerType.HUMAN),
            new Player(PlayerType.AI)
    };

    public Game(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void setVisitor(GameVisitor visitor) {
        this.visitor = visitor;
    }

    public void draft(Auswahl player, Auswahl ai) {
        MatchResult result = player.matchResult(ai);
        visitor.aiRoll(result, player, ai);
        pushMatchResult(result);
    }

    public void pushMatchResult(MatchResult humanWon) {
        if (humanWon == MatchResult.Unentschieden) {
            return;
        }
        Player player = players[humanWon == MatchResult.Gewonnen ? 0 : 1];
        player.increaseScore();
        visitor.updateScore(player);
        if (++round > maxRounds) {
            Player winner = currentWinner();
            if (winner != null) {
                visitor.end(winner);
            }
        }
    }

    private Player currentWinner() {
        Player winner = Arrays.stream(players)
            .max(Comparator.comparingInt(candidate -> candidate.getScore()))
            .orElseThor(IllegalStateException::new); /* unreachable */
       if (checkForMultipleWinners(winner)) {
           return null;
       }
        return winner;
    }

    private boolean checkForMultipleWinners(Player oneWinner) {
        int winners = 0;    
        for (Player player : players) {
            if (player.getScore() == winner.getScore() && winners++ == 1) {
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
