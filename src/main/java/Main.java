import com.formdev.flatlaf.FlatDarculaLaf;
import game.Controller;
import game.Game;
import gui.Gui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated( true );
        FlatDarculaLaf.setup();
        Game game = new Game();
        Controller controller = new Controller(game);
        game.setVisitor(new Gui(controller));
        game.start();
    }
}
