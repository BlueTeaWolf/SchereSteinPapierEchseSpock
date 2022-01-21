package game;

public class Controller {
    private Game running;

    private void setRunning(Game running) {
        this.running = running;
    }

    public Game newGame(int maxRounds) {
        Game game = new Game(maxRounds);
        setRunning(game);
        return game;
    }

    public void generateMove(Auswahl auswahl){
        int zufallsZahl = (int) (Math.random() * 5);
        Auswahl computer = Auswahl.values()[zufallsZahl];
        running.draft(auswahl, computer);
    }
}
