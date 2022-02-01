package game;

public class Player {
    private final PlayerType type;
    private final int localId;
    private int score;
    private Auswahl auswahl;

    public Player(PlayerType type, int localId) {
        this.type = type;
        this.localId = localId;
    }

    public Auswahl getAuswahl() {
        return auswahl;
    }

    public void setAuswahl(Auswahl auswahl) {
        this.auswahl = auswahl;
    }

    public String nameAndAuswahl() {
        if (auswahl == null) {
            return displayName();
        }
        return displayName() + " (" + auswahl + ")";
    }

    public String displayName() {
        return type.displayName() + " " + (localId + 1);
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
