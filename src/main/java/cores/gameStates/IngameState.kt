package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.clearAll
import cores.api.ImportantFunctions.closeAllInventories
import cores.api.ImportantFunctions.setGameModeAll
import cores.api.ImportantFunctions.setIngameItemsAll
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
            clearAll()
            setPlayersSurvival()
            teleportAllTeams()
            setIngameItemsAll()
            closeAllInventories()
            plugin.teamHelper.buildTeamInventory()
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
        }
    }
}
