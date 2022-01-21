package game;

public interface GameVisitor {
    void updateScore(Player player);
    void aiRoll(MatchResult matchResult, Auswahl your, Auswahl ai);
    void end(Player winner);
}
