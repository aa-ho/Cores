package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.WINNING_TEAM
import cores.api.ImportantFunctions.convertSeconds
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.PREFIX_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.getPlayersScoreboard
import cores.api.Messages.scoreboardLobbyCountdown
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.broadcastMessage
import org.bukkit.entity.Player
import org.bukkit.scoreboard.*
import org.bukkit.scoreboard.Scoreboard


//TODO CODE schöner machen scoreboard returnen? etc...
class Scoreboard {
    //TODO TOOD FIX THAT!!!
    //val lobbyScoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
    //private val objective: Objective = lobbyScoreboard.registerNewObjective("scoreboard", "dummy")

    fun updateLobbyScoreboard(p: Player) {
        val lobbyScoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = lobbyScoreboard.registerNewObjective("scoreboard", "dummy")

        objective.displayName = "§f§lNOOB-GAMES.NET"
        objective.displaySlot = DisplaySlot.SIDEBAR

        objective.getScore("").score = 12
        objective.getScore("Du spielst§8:").score = 11
        objective.getScore(PREFIX_COLORED).score = 10
        objective.getScore(" ").score = 9
        if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling) {
            objective.getScore("§fStart in§8:").score = 8
            objective.getScore(scoreboardLobbyCountdown(plugin.gameStateManager.lobbyState.lobbyCountdown.seconds)).score =
                7
        } else {
            objective.getScore("§fSpieler§8:").score = 8
            objective.getScore(getPlayersScoreboard()).score = 7
        }
        objective.getScore("  ").score = 6
        objective.getScore("§fMap§8:").score = 5
        objective.getScore("§eMünchen").score = 4
        objective.getScore("   ").score = 3
        objective.getScore("§fTeam§8:").score = 2
        objective.getScore(if (plugin.teamHelper.isPlayerInTeam(p)) if (plugin.teamHelper.getPlayerTeam(p) == Team.RED) RED_COLORED else BLUE_COLORED else Messages.RANDOM_TEAM_COLORED).score =
            1
        objective.getScore("    ").score = 0

        p.scoreboard = lobbyScoreboard
        setTabList(p)
    }
    private fun setTabList(p: Player) {
        val header = "§f§lNOOB-GAMES.NET"
        val footer = "§7Du bist auf §e$PREFIX_COLORED-1"
        p.setPlayerListHeaderFooter(header, footer)
    }


    fun updateInGameScoreboard(p: Player) {
        val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("scoreboard2", "dummy2")
        objective.displayName = "§f§lNOOB-GAMES.NET"
        objective.displaySlot = DisplaySlot.SIDEBAR
        objective.getScore(" ").score = 15
        objective.getScore("§fTimer:").score = 14
        objective.getScore("§7${convertSeconds(plugin.gameStateManager.ingameState.ingameTimer.seconds)}").score =
            13
        objective.getScore("").score = 12
        objective.getScore("${RED_COLORED}§8:").score = 11
        var count = 10
        for (redCores in plugin.beaconHelper.redCores) {
            objective.getScore("${redCores.key}-Core").score = count
            count--
        }
        objective.getScore("  ").score = 6
        objective.getScore("${BLUE_COLORED}§8:").score = 5
        count = 4
        for (blueCores in plugin.beaconHelper.blueCores) {
            objective.getScore("${blueCores.key}-Core ").score = count
            count--
        }
        p.scoreboard = scoreboard
    }
    fun updateEndGameScoreboard(p: Player) {
        val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("scoreboard3", "dummy3")
        objective.displayName = "§f§lNOOB-GAMES.NET"
        objective.displaySlot = DisplaySlot.SIDEBAR
        objective.getScore(" ").score = 15
        objective.getScore("§fDauer:").score = 14
        objective.getScore("§7${convertSeconds(plugin.gameStateManager.ingameState.ingameTimer.seconds)}").score =
            13
        objective.getScore("").score = 12
        objective.getScore("§fGewonnen:")
        objective.getScore("${WINNING_TEAM.colorDisplayed}${WINNING_TEAM.name}")
        p.scoreboard = scoreboard
    }
}