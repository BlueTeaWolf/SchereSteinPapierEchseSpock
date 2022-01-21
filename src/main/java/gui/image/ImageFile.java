import java.awt.Toolkit;

import javafx.scene.image.Image;

public class ImageFile {
    private final ImageIcon icon;
    private final ImageProducer imageSource;

    public ImageFile(String path) {
        icon = new ImageIcon("images/icons.png");
        imageSource = icon.getImage().getSource();
    }

    public Image image(Toolkit toolkit, Auswahl auswahl) {
        Image image = toolkit.createImage(auswahl.createSource(imageSource));
        return image.getScaledInstance(
                auswahl.getWidth() / 2,
                auswahl.getHeight() / 2,
                Image.SCALE_SMOOTH
        );
}
