package cores.gameStates

import cores.Main
import cores.Main.Companion.plugin
import cores.api.GlobalVars.TEAM_BLUE
import cores.api.GlobalVars.TEAM_RED
import cores.api.ImportantFunctions.clearAll
import cores.api.Messages
import cores.countdown.IngameTimer
import org.bukkit.Bukkit

class IngameState: GameState() {

    val ingameTimer = IngameTimer()

    override fun start() {
        Messages.sendStateToggled(GameStates.INGAME_STATE, true)
        if(!isRunning) {
            isRunning = true
            clearAll()
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
        if(TEAM_RED.size == 0 || TEAM_BLUE.size == 0) {
            plugin.gameStateManager.setGameState(GameStates.END_STATE)
        }
    }
}
