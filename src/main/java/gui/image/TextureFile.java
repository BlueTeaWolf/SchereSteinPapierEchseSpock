package gui.image;

import game.Auswahl;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.image.ImageProducer;
import javax.swing.ImageIcon;

public class TextureFile {
    private final ImageProducer imageSource;

    public TextureFile(String path) {
        ImageIcon icon = new ImageIcon(path);
        imageSource = icon.getImage().getSource();
    }

    private static final int SCALING = 2;

    public Image image(Toolkit toolkit, Auswahl auswahl) {
        Image image = toolkit.createImage(auswahl.createSource(imageSource));
        return image.getScaledInstance(
            auswahl.getWidth() / SCALING,
            auswahl.getHeight() / SCALING,
            Image.SCALE_SMOOTH
        );
    }
}
