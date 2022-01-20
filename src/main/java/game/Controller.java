package game;

public class Controller {
    private final Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void generateMove(Auswahl auswahl){
        int zufallsZahl = (int) (Math.random() * 5);
        Auswahl computer = Auswahl.values()[zufallsZahl];
        game.draft(auswahl, computer);
    }

}
