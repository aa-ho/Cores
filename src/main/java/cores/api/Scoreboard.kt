package cores.api

import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.PREFIX_COLORED
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard


class Scoreboard {
    var scoreBoard: Scoreboard
    val objective: Objective

    init {
        scoreBoard = Bukkit.getScoreboardManager().newScoreboard
        objective = scoreBoard.registerNewObjective("title", "dummy", PREFIX_COLORED)
        scoreBoard.registerNewObjective("players", "dummy", "§7Spieler: §b${PLAYERS.size}§7/§3$MAX_PLAYERS")
        scoreBoard.registerNewObjective("empty", "dummy", "§7")
        scoreBoard.registerNewObjective("map", "dummy", "§7Map: §bUnbekannt")
    }


    fun createScoreboard() {

        for (player in Bukkit.getOnlinePlayers()) {
            val score = objective.getScore(player)
            score.score = Bukkit.getOnlinePlayers().indexOf(player)
            objective.getScore(player.name).score = Bukkit.getOnlinePlayers().indexOf(player)
        }
        objective.displaySlot = DisplaySlot.SIDEBAR

        // Show the scoreboard to all players
        for (player in Bukkit.getOnlinePlayers()) {
            player.scoreboard = scoreBoard
        }
    }
}