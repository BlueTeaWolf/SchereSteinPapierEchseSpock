package game;

public class Player {
    private final PlayerType type;
    private int score;

    public Player(PlayerType type) {
        this.type = type;
    }

    public PlayerType getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }
}
