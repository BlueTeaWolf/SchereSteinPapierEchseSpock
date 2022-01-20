package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageProducer;

public class ImageButton extends JButton {
    public ImageButton(ImageProducer source) {
        this(Toolkit.getDefaultToolkit().createImage(source));
    }

    public ImageButton(Image image) {
        this(new ImageIcon(image));
    }

    public ImageButton(Icon icon) {
        //setBorderPainted(false);
        //setBorder(null);
        setFocusable(false);
        //setMargin(new Insets(0, 0, 0, 0));
        //setContentAreaFilled(false);
        setIcon(icon);
    }
}
