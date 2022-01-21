import java.text.DecimalFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import game.Controller;
import game.Game;

public class WelcomeView implements View {
    private static final String WELCOME_TEXT = "Willkommen!";
    private static final DecimalFormat roundFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);

    static {
        roundFormat.setGroupingUsed(false);
    }

    private final Controller controller;
    private final Gui gui;
    private final String text;

    public WelcomeView() {
        this(WELCOME_TEXT);
    }

    public WelcomeView(String text) {
        this.text = text;
    }

    @Override
    public void renderInto(JPanel panel) {
        panel.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(new JLabel(text, SwingConstants.CENTER));
        JFormattedTextField rounds = new JFormattedTextField(roundFormat);
        rounds.setText("5");
        panel.add(rounds);
        JButton startButton = new JButton(text.equals(WELCOME_TEXT) ? "Spiele" : "Nochmal spielen");
        startButton.addActionListener(e -> startGame(Integer.parseInt(rounds.getText())));
        panel.add(startButton);
        panel.add(Box.createVerticalGlue());
    }

    private void startGame(int maxRounds) {
        Game game = controller.newGame(maxRounds);
        GameView view = new GameView(controller, gui);
        game.setVisitor(view);
    }
}
