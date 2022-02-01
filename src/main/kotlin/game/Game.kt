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

class Game(private val maxRounds: Int, playerConfiguration: PlayerConfiguration) {
    private var round = 0
    private var visitor: GameVisitor? = null
    private val players: Array<Player>
    private var drafter = 0

    init {
        players = playerConfiguration.build()
    }

    fun players(): Array<Player> {
        return players
    }

    fun advanceDraft(auswahl: Auswahl?) {
        val current = players[drafter]
        current!!.auswahl = auswahl
        visitor!!.draw(current)
        drafter++
        if (drafter >= players.size) {
            pushMatchResult()
        } else if (players[drafter]?.type == PlayerType.AI) {
            val zufallsZahl = (Math.random() * 5).toInt()
            advanceDraft(Auswahl.values()[zufallsZahl])
        }
    }

    private fun resetRound() {
        drafter = 0
        for (player in players) {
            player?.auswahl = null
        }
    }

    fun setVisitor(visitor: GameVisitor?) {
        this.visitor = visitor
    }

    fun pushMatchResult() {
        val result = RoundResult(players)
        val winners = result.winners()
        val loosers = result.loosers()
        visitor!!.roundComplete(result)
        resetRound()
        result.mayRewardPlayers()
        for (player in players!!) {
            visitor!!.updateScore(player)
        }
        if (++round > maxRounds) {
            val winner = currentWinner()
            if (winner != null) {
                visitor!!.end(winner)
            }
        }
    }

    private fun currentWinner(): Player? {
        val winner = Arrays.stream(players)
                .max(Comparator.comparingInt { obj: Player -> obj.score })
                .orElseThrow { IllegalStateException() } /* unreachable */
        return if (checkForMultipleWinners(winner)) {
            null
        } else winner
    }

    private fun checkForMultipleWinners(oneWinner: Player?): Boolean {
        var winners = 0
        for (player in players) {
            if (player.score == oneWinner?.score && winners++ == 1) {
                return true
            }
        }
        return false
    }

    fun start() {
        for (player in players!!) {
            visitor!!.updateScore(player)
        }
    }
}