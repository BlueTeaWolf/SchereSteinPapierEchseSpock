import game.Controller;
import game.GameVisitor;
import gui.Gui;

public class GameView implements View, GameVisitor {
    private static final ImageIcon icon = new ImageIcon("images/icons.png");
    private static final IImageProducer imageSource = icon.getImage().getSource();

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
    public void renderInto(JPanel panel) {
        panel.setLayout(new BorderLayout());
        panel.add(top(), BorderLayout.PAGE_START);
        JPanel buttons = new JPanel();
        for (Auswahl value : Auswahl.values()) {
            JButton button = new ImageButton(image(value, imageSource));
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
        panel.setLayout(new GridLayout(1, 3));
        return panel;
    }

    @Override
    public void updateScore(Player player) {
        switch (player.getType()) {
            case HUMAN -> ownScore.setText("Dein Score: " + player.getScore());
            case AI -> aiScore.setText("Ai Score: " + player.getScore());
        }
    }

    @Override
    public void aiRoll(MatchResult matchResult, Auswahl your, Auswahl ai) {
        status.setText(
                switch (matchResult) {
                    case Verloren -> your.name() + " looses against " + ai.name();
                    case Gewonnen -> your.name() + " wins against " + ai.name();
                    case Unentschieden -> your.name() + " goes even with " + ai.name();
                }
        );
    }
}
