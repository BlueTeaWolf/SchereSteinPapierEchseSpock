package game;

public interface GameVisitor {
    void updateScore(Player player);

    void draw(Player player);

    void roundComplete(RoundResult roundResult);

    void end(Player winner);
}
