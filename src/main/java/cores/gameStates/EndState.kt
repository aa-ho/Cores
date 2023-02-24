package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.ImportantFunctions.teleportAll
import cores.api.Messages
import cores.countdown.EndStateCountdown
import org.bukkit.Bukkit

class EndState: GameState() {

    val endStateCountdown = EndStateCountdown()
    override fun start() {
        Messages.sendStateToggled(GameStates.END_STATE, true)
        if(!isRunning) {
            isRunning = true
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