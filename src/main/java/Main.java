import com.formdev.flatlaf.FlatDarculaLaf;
import game.Controller;
import game.Game;
import gui.Gui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated( true );
        FlatDarculaLaf.setup();
        Controller controller = new Controller();
        Gui gui = new Gui(controller);
        gui.showToScreen();
    }
}
