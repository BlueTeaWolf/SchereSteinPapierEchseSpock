package gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import game.*;
import gui.view.View;

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
    private View view = new WelcomeView();

    {
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 12));
    }

    public Gui(Controller controller) {
        view = new WelcomeView(controller, this);
        setTitle("Schere, Stein, Papier, Echse und Spock");
        setSize(1300, 450);
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
        getContentPane().setForeground(Color.white);
    }

    public void applyView() {
        setContentDefaults();
        view.renderInto(getContentPane());
    }
}
