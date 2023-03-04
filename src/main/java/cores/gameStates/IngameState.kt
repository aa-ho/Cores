package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.resetAllPlayers
import cores.api.ImportantFunctions.setIngameItemsForPlayers
import cores.api.ImportantFunctions.setPlayersSurvival
import cores.api.ImportantFunctions.teleportAllTeams
import cores.api.Messages
import cores.api.Messages.setTeamHasWon
import cores.api.Team
import cores.countdown.InGameIdle
import cores.countdown.IngameTimer
import org.bukkit.Bukkit

class IngameState : GameState() {

    val ingameTimer = IngameTimer()
    val inGameIdle = InGameIdle()

    override fun start() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, true)
        if (!isRunning) {
            isRunning = true
            plugin.teamHelper.assignPlayers()
            plugin.stats.addPlayersToStats()
            setPlayersSurvival()
            teleportAllTeams()
            resetAllPlayers()
            setIngameItemsForPlayers()
            ingameTimer.start()
            inGameIdle.start()
        }
    }

    override fun stop() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, false)
        if (isRunning) {
            Bukkit.getScheduler().cancelTask(taskID)
            isRunning = false
            ingameTimer.stop()
            inGameIdle.stop()
        }
    }

    fun isGameOver() {
        if (!plugin.beaconHelper.hasTeamCores(Team.RED)) {
            setTeamHasWon(Team.BLUE)
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        } else if (!plugin.beaconHelper.hasTeamCores(Team.BLUE)) {
            setTeamHasWon(Team.RED)
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        } else {
            if (plugin.teamHelper.teamSize(Team.RED) == 0) {
                setTeamHasWon(Team.BLUE)
                plugin.gameStateManager.setGameState(GameStates.END_STATE)
            } else if (plugin.teamHelper.teamSize(Team.BLUE) == 0) {
                setTeamHasWon(Team.RED)
                plugin.gameStateManager.setGameState(GameStates.END_STATE)
            } else {
                var count = 0
                PLAYERS.forEach {
                    if (it.value) count++
                    if (count > 1) return
                }
                //TODO vielleicht anders lösen?
                setTeamHasWon(plugin.teamHelper.getPlayerTeam(PLAYERS.entries.first().key))
                plugin.gameStateManager.setGameState(GameStates.END_STATE)
            }
        }
    }
}
