package cores.gameStates

import cores.api.Messages
import cores.countdown.EndStateCountdown
import org.bukkit.Bukkit

class EndState: GameState() {

    val endStateCountdown = EndStateCountdown()
    override fun start() {
        Messages.sendStateToggled(GameStates.END_STATE, true)
        if(!isRunning) {
            isRunning = true
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