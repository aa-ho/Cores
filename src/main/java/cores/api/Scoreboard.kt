package cores.api

import cores.Main.Companion.plugin
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.LETS_GO
import cores.api.Messages.PREFIX_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.getPlayersScoreboard
import cores.api.Messages.scoreboardLobbyCountdown
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.*
import org.bukkit.scoreboard.Scoreboard


//TODO CODE schöner machen scoreboard returnen? etc...
class Scoreboard {
    fun setLobbyScoreboardOld(p: Player) {
        val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("scoreboard", "dummy")
        objective.displayName = "§f§lNOOB-GAMES.NET"
        objective.displaySlot = DisplaySlot.SIDEBAR
        val emptyLine: Score = objective.getScore("")
        emptyLine.score = 12
        val serverLine: Score = objective.getScore("Du spielst§8:")
        serverLine.score = 11
        val gameLine: Score = objective.getScore(PREFIX_COLORED)
        gameLine.score = 10
        val emptyLine5: Score = objective.getScore("     ")
        emptyLine5.score = 9
        if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling) {
            val countDownLineText: Score = objective.getScore("§fStart in§8:")
            countDownLineText.score = 8
            val countDownLine: Score =
                objective.getScore(scoreboardLobbyCountdown(plugin.gameStateManager.lobbyState.lobbyCountdown.seconds))
            countDownLine.score = 7
        } else {
            val playersLineText: Score = objective.getScore("§fSpieler§8:")
            playersLineText.score = 8
            val playersLine: Score = objective.getScore(getPlayersScoreboard())
            playersLine.score = 7
        }
        val emptyLine2: Score = objective.getScore(" ")
        emptyLine2.score = 6
        val mapTextLine: Score = objective.getScore("§fMap§8:")
        mapTextLine.score = 5
        val mapText: Score = objective.getScore("§eMünchen")
        mapText.score = 4
        val emptyLine3: Score = objective.getScore("  ")
        emptyLine3.score = 3
        val teamTextLine: Score = objective.getScore("§fTeam§8:")
        teamTextLine.score = 2
        val teamLine: Score =
            objective.getScore(if (plugin.teamHelper.isPlayerInTeam(p)) if (plugin.teamHelper.getPlayerTeam(p) == Teams.RED) RED_COLORED else BLUE_COLORED else Messages.RANDOM_TEAM_COLORED)
        teamLine.score = 1
        val emptyLine4: Score = objective.getScore("   ")
        emptyLine4.score = 0
        p.scoreboard = scoreboard
    }
    fun setLobbyScoreboard(p: Player) {
        val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("scoreboard", "dummy")
        objective.displayName = "§f§lNOOB-GAMES.NET"
        objective.displaySlot = DisplaySlot.SIDEBAR

        objective.getScore("").score = 12
        objective.getScore("Du spielst§8:").score = 11
        objective.getScore(PREFIX_COLORED).score = 10

        if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling) {
            objective.getScore("§fStart in§8:").score = 8
            objective.getScore(scoreboardLobbyCountdown(plugin.gameStateManager.lobbyState.lobbyCountdown.seconds)).score = 7
        } else {
            objective.getScore("§fSpieler§8:").score = 8
            objective.getScore(getPlayersScoreboard()).score = 7
        }

        objective.getScore(" ").score = 6
        objective.getScore("§fMap§8:").score = 5
        objective.getScore("§eMünchen").score = 4
        objective.getScore("  ").score = 3
        objective.getScore("§fTeam§8:").score = 2
        objective.getScore(if (plugin.teamHelper.isPlayerInTeam(p)) if (plugin.teamHelper.getPlayerTeam(p) == Teams.RED) RED_COLORED else BLUE_COLORED else Messages.RANDOM_TEAM_COLORED).score = 1
        objective.getScore("   ").score = 0

        p.scoreboard = scoreboard
    }
}