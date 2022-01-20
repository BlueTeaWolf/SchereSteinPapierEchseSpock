package game;

import javax.swing.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.util.Arrays;
import java.util.List;

public enum Auswahl {
    Stein(80, 350),
    Papier(430, 350),
    Schere(860, 350),
    Echse(1310, 350),
    Spock(1690, 350);

    static {
        Schere.setAuswahl(Echse, Papier);
        Stein.setAuswahl(Schere, Echse);
        Papier.setAuswahl(Spock, Stein);
        Echse.setAuswahl(Spock, Papier);
        Spock.setAuswahl(Schere, Stein);
    }

    private List<Auswahl> auswahl;
    private final int x, y = 100, width, height = 440 - y;

    Auswahl(int x, int width) {
        this.x = x;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Auswahl> getAuswahl() {
        return auswahl;
    }

   public ImageProducer createSource(ImageProducer source) {
       return new FilteredImageSource(
               source,
               new CropImageFilter(x, y, width, height)
       );
   }

    private void setAuswahl(Auswahl... auswahl) {
        this.auswahl = Arrays.asList(auswahl);
    }

    public MatchResult matchResult(Auswahl auswahl) {
        if (auswahl == this)
            return MatchResult.Unentschieden;
        if (this.auswahl.contains(auswahl)){
            return MatchResult.Gewonnen;
        }
        return MatchResult.Verloren;
    }
}
