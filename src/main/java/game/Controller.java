package game;

public class Controller {
    private Game running;

    private void setRunning(Game running) {
        this.running = running;
    }

    public Game newGame() {
        Game game = new Game();
        setRunning(game);
        return game;
    }

    public void generateMove(Auswahl auswahl){
        int zufallsZahl = (int) (Math.random() * 5);
        Auswahl computer = Auswahl.values()[zufallsZahl];
        game.draft(auswahl, computer);
    }
}
