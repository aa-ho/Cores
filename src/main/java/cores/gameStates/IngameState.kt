package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.resetAllPlayers
import cores.api.ImportantFunctions.setIngameItemsForPlayers
import cores.api.ImportantFunctions.setPlayersSurvival
import cores.api.ImportantFunctions.teleportAllTeams
import cores.api.Messages
import cores.api.Team
import cores.countdown.IngameTimer
import org.bukkit.Bukkit

class IngameState: GameState() {

    val ingameTimer = IngameTimer()

    override fun start() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, true)
        if(!isRunning) {
            isRunning = true
            plugin.teamHelper.assignPlayers()
            setPlayersSurvival()
            teleportAllTeams()
            resetAllPlayers()
            setIngameItemsForPlayers()
            ingameTimer.start()
        }
    }

    override fun stop() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, false)
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID)
            isRunning = false
            ingameTimer.stop()
        }
    }

    fun isGameOver() {
        if(plugin.teamHelper.teamSize(Team.BLUE) == 0 || plugin.teamHelper.teamSize(Team.RED) == 0) {
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        } else {
            var count = 0
            PLAYERS.forEach {
                if(it.value) count++
                if(count>1) return
            }
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        }
    }
}
