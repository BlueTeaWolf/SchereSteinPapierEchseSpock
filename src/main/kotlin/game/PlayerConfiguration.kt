package game

import game.PlayerConfiguration
import game.GameVisitor
import game.Player
import game.Auswahl
import game.PlayerType
import game.RoundResult
import java.util.function.ToIntFunction
import java.lang.IllegalStateException
import java.util.function.Supplier
import java.awt.image.ImageProducer
import java.awt.image.FilteredImageSource
import java.awt.image.CropImageFilter
import game.Game
import java.util.*
import java.util.function.BiConsumer

class PlayerConfiguration {
    private val players: MutableMap<PlayerType, Int> = EnumMap(PlayerType::class.java)
    private var offset = 0
    private lateinit var array: Array<Player?>;

    fun add(type: PlayerType, count: Int): PlayerConfiguration {
        players[type] = count
        return this
    }

    private fun total(): Int {
        return players.values.sum()
    }

    private fun fillType(type: PlayerType, count: Int) {
        for (i in 0 until count) {
            array[offset++] = Player(type, i)
        }
    }

    fun build(): Array<Player> {
        array = arrayOfNulls(total())
        players.forEach { (type, count) -> fillType(type, count) }
        return array.requireNoNulls()
    }
}