package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.waitingForXPlayers
import org.bukkit.Bukkit

class LobbyIdle : Countdown() {



    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                if (PLAYERS.isNotEmpty()) {
                    if (PLAYERS.size < MIN_PLAYERS) {
                        waitingForXPlayers(MIN_PLAYERS - PLAYERS.size)
                    }
                }
            }, 0, 600)
        }
    }

    override fun stop() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(taskID)
            isIdling = false
            plugin.gameStateManager.lobbyState.lobbyCountdown.start()
        }
    }
}