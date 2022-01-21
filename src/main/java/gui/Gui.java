package gui;

import game.Controller;
import gui.view.View;
import gui.view.WelcomeView;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Gui extends JFrame {
    private View view;

    {
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 12));
    }

    public Gui(Controller controller) {
        view = new WelcomeView(controller, this);
        setTitle("Schere, Stein, Papier, Echse und Spock");
        setSize(1300, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setBackground(Color.decode("#202225"));
    }

    public void showToScreen() {
        applyView();
        setResizable(false);
        setVisible(true);
    }

    public void setView(View view) {
        this.view = view;
        applyView();
    }

    private void setContentDefaults() {
        getContentPane().removeAll();
        getContentPane().setForeground(Color.white);
    }

    public void applyView() {
        setContentDefaults();
        view.renderInto(getContentPane());
        getContentPane().repaint();
    }
}
