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

class RoundResult(players: Array<Player>?) {
    private val winners: MutableCollection<Player> = ArrayList()
    private val loosers: MutableCollection<Player> = ArrayList()

    init {
        val winMap: MutableMap<Player, Int> = HashMap()
        var heighestScore = Int.MIN_VALUE
        for (player in players!!) {
            var score = 0
            for (opponent in players) {
                when (player.auswahl?.matchResult(opponent.auswahl)) {
                    MatchResult.Verloren -> score--
                    MatchResult.Gewonnen -> score++
                }
            }
            if (score > heighestScore) {
                heighestScore = score
            }
            winMap[player] = score
        }
        for ((key, value) in winMap) {
            (if (value == heighestScore) winners else loosers).add(key)
        }
    }

    fun multipleWinners(): Boolean {
        return winners().size > 1
    }

    val isDraw: Boolean
        get() = loosers.isEmpty()

    fun winners(): Collection<Player> {
        return winners
    }

    fun loosers(): Collection<Player> {
        return loosers
    }

    fun mayRewardPlayers() {
        if (!loosers.isEmpty()) {
            for (winner in winners) {
                winner!!.increaseScore()
            }
        }
    }
}