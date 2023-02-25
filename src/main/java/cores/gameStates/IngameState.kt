package cores.gameStates

import cores.Main
import cores.Main.Companion.gameStateManager
import cores.Main.Companion.plugin
import cores.Main.Companion.teamHelper
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.clearAll
import cores.api.ImportantFunctions.closeAllInventories
import cores.api.ImportantFunctions.setIngameItemsAll
import cores.api.ImportantFunctions.setIngamePlayerItems
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
            teamHelper.buildTeamInventory()
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
        if(teamHelper.teamSize(Teams.BLUE) == 0 || teamHelper.teamSize(Teams.RED) == 0) {
            gameStateManager.setGameState(GameStates.END_STATE)
        }
    }
}
