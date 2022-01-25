package game;

import java.util.Collection;

public interface GameVisitor {
    void updateScore(Player player);

    void draw(Player player);

    void roundComplete(Collection<Player> winners, Collection<Player> loosers);

    void end(Player winner);
}
