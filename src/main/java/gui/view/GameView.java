package gui.view;

import game.*;
import gui.Gui;
import gui.image.ImageButton;
import gui.image.TextureFile;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.swing.*;

public class GameView implements View, GameVisitor {
    private static final TextureFile IMAGE_FILE = new TextureFile("images/icons.png");

    private final Controller controller;
    private final Gui gui;
    private final JLabel ownScore = new JLabel("", SwingConstants.LEFT);
    private final JLabel aiScore = new JLabel("", SwingConstants.RIGHT);
    private final JLabel status = new JLabel("Bild anklicken um zu starten");

    public GameView(Controller controller, Gui gui) {
        this.controller = controller;
        this.gui = gui;
    }

    @Override
    public void renderInto(Container panel) {
        panel.setLayout(new BorderLayout());
        panel.add(top(), BorderLayout.PAGE_START);
        JPanel buttons = new JPanel();
        for (Auswahl value : Auswahl.values()) {
            JButton button = new ImageButton(IMAGE_FILE.image(panel.getToolkit(), value));
            button.addActionListener(e -> controller.generateMove(value));
            buttons.add(button);
        }
        buttons.setBackground(Color.decode("#36393f"));
        //buttons.setLayout(new GridLayout(1, 5, 10, 0));
        panel.add(buttons, BorderLayout.CENTER);
    }

    private JPanel top() {
        JPanel panel = new JPanel();
        panel.add(ownScore);
        panel.add(status);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(aiScore);
        panel.setBackground(Color.decode("#2f3136"));
        for (Component component : panel.getComponents()) {
            component.setFont(new Font("Arial", Font.PLAIN, 22));
            component.setForeground(Color.WHITE);
        }
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        //panel.setLayout(new FlowLayout());
        //panel.setLayout(new GridLayout(1, 3));
        return panel;
    }

    @Override
    public void draw(Player player) {
        updateScore(player);
    }

    @Override
    public void updateScore(Player player) {
        String text = "<html>";
        for (Player member : controller.currentGame().players()) {
            if (member.getType() == player.getType()) {
                text += member.nameAndAuswahl() + ": " + member.getScore() + "<br>";
            }
        }
        text = text.substring(0, text.length() - 4);
        text += "</html>";
        switch (player.getType()) {
            case HUMAN:
                ownScore.setText(text);
                break;
            case AI:
                aiScore.setText(text);
                break;
        }
    }

    @Override
    public void roundComplete(RoundResult roundResult) {
        if (roundResult.isDraw()) {
            status.setText("Unenschieden");
            return;
        }
        String text = "";
        text += formatPlayers(roundResult.winners());
        text += " " + (roundResult.multipleWinners() ? "gewinnen" : "gewinnt") + " gegen ";
        text += formatPlayers(roundResult.loosers());
        status.setText(text);
    }

    private static String formatPlayers(Collection<Player> players) {
        return players.stream().map(Player::nameAndAuswahl).collect(Collectors.joining(", "));
    }

    @Override
    public void end(Player winner) {
        gui.setView(new WelcomeView(controller, gui, winner.displayName() + " won!"));
    }
}
