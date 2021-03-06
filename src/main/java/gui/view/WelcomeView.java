package gui.view;

import game.Controller;
import game.Game;
import game.PlayerConfiguration;
import game.PlayerType;
import gui.Gui;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

public class WelcomeView implements View {
    private static final String WELCOME_TEXT = "Willkommen!";
    private static final DecimalFormat roundFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);

    static {
        roundFormat.setGroupingUsed(false);
    }

    private final Controller controller;
    private final Gui gui;
    private final String text;

    public WelcomeView(Controller controller, Gui gui) {
        this(controller, gui, WELCOME_TEXT);
    }

    public WelcomeView(Controller controller, Gui gui, String text) {
        this.controller = controller;
        this.gui = gui;
        this.text = text;
    }

    private JLabel text() {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 38));
        return label;
    }

    private NumberFormatter roundFormatter() {
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance()) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.length() == 0)
                    return null;
                return super.stringToValue(text);
            }
        };;
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(200);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return formatter;
    }

    @Override
    public void renderInto(Container panel) {
        panel.setLayout(new GridBagLayout());
        panel.add(text(), new GridArea().height(4).width(2).verticalInsets(20, 80));
        JFormattedTextField ai = numberField("1");
        JFormattedTextField player = numberField("1");
        panel.add(new JLabel("AIs: "), new GridArea().offsetY(5));
        panel.add(ai, new GridArea().offsetY(5));
        panel.add(new JLabel("Players: "), new GridArea().offsetY(6));
        panel.add(player, new GridArea().offsetY(6));
        panel.add(new JLabel("Rounds: "), new GridArea().offsetY(7).verticalInsets(15, 0));

        JFormattedTextField rounds = numberField("5");
        panel.add(rounds, new GridArea().offsetY(7).verticalInsets(15, 0));
        JButton startButton = new JButton(text.equals(WELCOME_TEXT) ? "Spielen" : "Nochmal spielen");
        startButton.addActionListener(event -> {
            startGame((Integer) rounds.getValue(), new PlayerConfiguration()
                    .add(PlayerType.HUMAN, (Integer) player.getValue())
                    .add(PlayerType.AI, (Integer) ai.getValue()));
        });
        rounds.addActionListener(event -> startButton.doClick());
        panel.add(startButton, new GridArea().offsetY(8).width(2).verticalInsets(15, 0));
    }

    private JFormattedTextField numberField(String def) {
        JFormattedTextField rounds = new JFormattedTextField(roundFormatter());
        rounds.setText(def);
        rounds.setColumns(15);
        return rounds;
    }

    private void startGame(Integer maxRounds, PlayerConfiguration configuration) {
        Game game = controller.newGame(maxRounds == null ? 3 : maxRounds, configuration);
        GameView view = new GameView(controller, gui);
        game.setVisitor(view);
        gui.setView(view);
        game.start();
    }
}
