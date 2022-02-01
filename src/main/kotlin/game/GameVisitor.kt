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

interface GameVisitor {
    fun updateScore(player: Player?)
    fun draw(player: Player?)
    fun roundComplete(roundResult: RoundResult?)
    fun end(winner: Player?)
}