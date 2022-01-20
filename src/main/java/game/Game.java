package game;

public class Game {
    private GameVisitor visitor;
    private final Player[] players = new Player[] {
            new Player(PlayerType.HUMAN),
            new Player(PlayerType.AI)
    };

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
        Player player = players[humanWon == MatchResult.Gewonnen? 0 : 1];
        player.increaseScore();
        visitor.updateScore(player);
    }

    public void start() {
        for (Player player : players) {
            visitor.updateScore(player);
        }
    }
}
