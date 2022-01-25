package game;

public class Controller {
    private Game running;

    private void setRunning(Game running) {
        this.running = running;
    }

    public Game newGame(int maxRounds, PlayerConfiguration playerConfiguration) {
        Game game = new Game(maxRounds, playerConfiguration);
        setRunning(game);
        return game;
    }

    public Game currentGame() {
        return running;
    }

    public void generateMove(Auswahl auswahl){
        running.advanceDraft(auswahl);
    }
}
