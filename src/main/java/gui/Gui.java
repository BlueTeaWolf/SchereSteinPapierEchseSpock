package gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import game.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.RootPaneUI;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

public class Gui extends JFrame implements GameVisitor {
    private final Controller controller;
    private final JLabel ownScore = new JLabel("", SwingConstants.LEFT);
    private final JLabel aiScore = new JLabel("", SwingConstants.RIGHT);
    private final JLabel status = new JLabel("Bild anklicken um zu starten");

    {
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 12));
    }

    public Gui(Controller controller) {
        this.controller = controller;
        ImageIcon icon = new ImageIcon("images/icons.png");
        ImageProducer imageSource = icon.getImage().getSource();
        setTitle("Schere, Stein, Papier, Echse und Spock");
        setSize(1300, 450);
        getRootPane().setBackground(Color.decode("#202225"));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top(), BorderLayout.PAGE_START);
        JPanel buttons = new JPanel();
        for (Auswahl value : Auswahl.values()) {
            JButton button = new ImageButton(image(value, imageSource));
            button.addActionListener(e -> controller.generateMove(value));
            buttons.add(button);
        }
        buttons.setBackground(Color.decode("#36393f"));
        //buttons.setLayout(new GridLayout(1, 5, 10, 0));
        getContentPane().add(buttons, BorderLayout.CENTER);
        getContentPane().setForeground(Color.white);
        setResizable(false);
        setVisible(true);
    }

    private Image image(Auswahl auswahl, ImageProducer source) {
        Image image = createImage(auswahl.createSource(source));
        return image.getScaledInstance(
                auswahl.getWidth() / 2,
                auswahl.getHeight() / 2,
                Image.SCALE_SMOOTH
        );
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
