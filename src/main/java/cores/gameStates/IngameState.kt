package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.ImportantFunctions.clearAll
import cores.api.ImportantFunctions.closeAllInventories
import cores.api.ImportantFunctions.setIngameItemsAll
import cores.api.Messages
import cores.api.Teams
import cores.countdown.IngameTimer
import org.bukkit.Bukkit

class IngameState: GameState() {

    val ingameTimer = IngameTimer()

    override fun start() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, true)
        if(!isRunning) {
            isRunning = true
            clearAll()
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
        if(plugin.teamHelper.teamSize(Teams.BLUE) == 0 || plugin.teamHelper.teamSize(Teams.RED) == 0) {
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        }
    }
}
