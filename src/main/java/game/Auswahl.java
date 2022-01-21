package game;

import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.util.Arrays;
import java.util.List;

public enum Auswahl {
    Stein(60, 350),
    Papier(430, 350),
    Schere(860, 350),
    Echse(1260, 350),
    Spock(1660, 350);

    static {
        Schere.setWinsAgainst(Echse, Papier);
        Stein.setWinsAgainst(Schere, Echse);
        Papier.setWinsAgainst(Spock, Stein);
        Echse.setWinsAgainst(Spock, Papier);
        Spock.setWinsAgainst(Schere, Stein);
    }

    private List<Auswahl> winsAgainst;
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

   public ImageProducer createSource(ImageProducer source) {
       return new FilteredImageSource(
               source,
               new CropImageFilter(x, y, width, height)
       );
   }

    private void setWinsAgainst(Auswahl... winsAgainst) {
        this.winsAgainst = Arrays.asList(winsAgainst);
    }

    public MatchResult matchResult(Auswahl auswahl) {
        if (auswahl == this)
            return MatchResult.Unentschieden;
        if (this.winsAgainst.contains(auswahl)){
            return MatchResult.Gewonnen;
        }
        return MatchResult.Verloren;
    }
}
