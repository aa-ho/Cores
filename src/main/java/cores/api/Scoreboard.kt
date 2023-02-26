package cores.api

import cores.Main.Companion.plugin

import cores.api.Messages.BLUE_COLORED
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

    //TODO endlessjump...
/*    fun setTablist(p: Player) {
        val header = "§6Du bist auf §eLobby-1"
        val footer = "§6EndlessJump.net §8§l✘ §eServernetzwerk"
        p.setPlayerListHeaderFooter(header, footer)
    }*/
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
        objective.getScore("").score = 12
        objective.getScore("§fTeam§8:").score = 2
        objective.getScore(if (plugin.teamHelper.isPlayerInTeam(p)) if (plugin.teamHelper.getPlayerTeam(p) == Team.RED) RED_COLORED else BLUE_COLORED else Messages.SPECTATOR_COLORED).score =
            1
        objective.getScore("    ").score = 0
        p.scoreboard = scoreboard
    }
}