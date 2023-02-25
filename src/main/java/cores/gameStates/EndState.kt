package cores.gameStates

import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.ImportantFunctions
import cores.api.ImportantFunctions.clearAll
import cores.api.ImportantFunctions.closeAllInventories
import cores.api.ImportantFunctions.setGameModeAll
import cores.api.ImportantFunctions.teleportAll
import cores.api.Messages
import cores.countdown.EndStateCountdown
import org.bukkit.Bukkit
import org.bukkit.GameMode

class EndState: GameState() {

    val endStateCountdown = EndStateCountdown()
    override fun start() {
        Messages.sendStateToggled(GameStates.END_STATE, true)
        if(!isRunning) {
            isRunning = true
            clearAll()
            closeAllInventories()
            setGameModeAll(GameMode.ADVENTURE)
            teleportAll(LOBBY_SPAWN_LOCATION)
            endStateCountdown.start()
        }
    }

    override fun stop() {
        Messages.sendStateToggled(GameStates.END_STATE, false)
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID)
            isRunning = false
            endStateCountdown.stop()
        }
    }
}