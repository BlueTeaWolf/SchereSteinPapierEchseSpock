package gui.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * @author David (_Esel)
 */
public class GridArea extends GridBagConstraints {
    public GridArea height(int height) {
        gridheight = height;
        return this;
    }

    public enum FillMode {
        NONE(0),
        VERTICAL(3),
        HORIZONTAL(2),
        BOTH(1);

        FillMode(int asInt) {
            this.asInt = asInt;
        }

        private final int asInt;
    }

    public GridArea insets(int top, int left, int bottom, int right) {
        return insets(new Insets(top, left, bottom, right));
    }

    public GridArea verticalInsets(int top, int bottom) {
        return insets(new Insets(top, 0, bottom, 0));
    }

    public GridArea insets(Insets insets) {
        this.insets = insets;
        return this;
    }

    public GridArea fill(FillMode mode) {
        fill = mode.asInt;
        return this;
    }

    public GridArea width(int width) {
        gridwidth = width;
        return this;
    }

    public GridArea offsetY(int yOffset) {
        gridy = yOffset;
        return this;
    }
}
