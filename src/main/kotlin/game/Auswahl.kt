package game

import game.PlayerConfiguration
import game.GameVisitor
import game.Player
import game.Auswahl
import game.PlayerType
import game.RoundResult
import java.util.Arrays
import java.util.function.ToIntFunction
import java.lang.IllegalStateException
import java.util.function.Supplier
import java.awt.image.ImageProducer
import java.awt.image.FilteredImageSource
import java.awt.image.CropImageFilter
import game.Game
import java.util.Locale
import java.util.HashMap
import java.util.EnumMap
import java.util.function.BiConsumer

enum class Auswahl(private val x: Int, val width: Int) {
    Stein(60, 350), Papier(430, 350), Schere(860, 350), Echse(1260, 350), Spock(1660, 350);

    private var winsAgainst: List<Auswahl>? = null
    private val y = 100
    val height = 440 - y

    fun createSource(source: ImageProducer?): ImageProducer {
        return FilteredImageSource(
                source,
                CropImageFilter(x, y, width, height)
        )
    }

    private fun setWinsAgainst(vararg winsAgainst: Auswahl) {
        this.winsAgainst = Arrays.asList(*winsAgainst)
    }

    fun matchResult(auswahl: Auswahl?): MatchResult {
        if (auswahl == this) return MatchResult.Unentschieden
        return if (winsAgainst!!.contains(auswahl)) {
            MatchResult.Gewonnen
        } else MatchResult.Verloren
    }

    companion object {
        init {
            Schere.setWinsAgainst(Echse, Papier)
            Stein.setWinsAgainst(Schere, Echse)
            Papier.setWinsAgainst(Spock, Stein)
            Echse.setWinsAgainst(Spock, Papier)
            Spock.setWinsAgainst(Schere, Stein)
        }
    }
}