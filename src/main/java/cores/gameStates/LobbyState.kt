package cores.gameStates

import cores.Main.Companion.plugin
import cores.api.Messages.sendConsole
import cores.api.Messages.sendStateToggled
import cores.countdown.LobbyCountdown
import cores.countdown.LobbyIdle
import org.bukkit.Bukkit

class LobbyState: GameState() {

    val lobbyIdle = LobbyIdle()
    val lobbyCountdown = LobbyCountdown()
    override fun start() {
        sendStateToggled(GameStates.LOBBY_STATE, true)
        if(!isRunning) {
            isRunning = true
            lobbyIdle.start()
        }
    }

    override fun stop() {
        sendStateToggled(GameStates.LOBBY_STATE, false)
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID)
            isRunning = false
        }
    }
}