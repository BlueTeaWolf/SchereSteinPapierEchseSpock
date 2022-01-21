import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Controller;
import game.Game;

public class WelcomeView implements View {
    private final Controller controller;
    private final Gui gui;
    private final String text;

    public WelcomeView() {
        this("Willkommen!");
    }

    public WelcomeView(String text) {
        this.text = text;
    }

    @Override
    public void renderInto(JPanel panel) {
        panel.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(new JLabel(text, SwingConstants.CENTER));
        JButton startButton = new JButton("Spiele");
        startButton.addActionListener(e -> startGame());
        panel.add(startButton);
        panel.add(Box.createVerticalGlue());
    }

    private void startGame() {
        Game game = controller.newGame();
        GameView view = new GameView(controller, gui);
        game.setVisitor(view);
    }
}
