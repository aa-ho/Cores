package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalConst.GAME_START_SOUND
import cores.api.GlobalVars.PLAYERS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SECONDS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SOUND
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.ALLOW_JOIN
import cores.api.GlobalVars.GAME_STARTING
import cores.api.ImportantFunctions.playSoundForAll
import cores.api.ImportantFunctions.sendTitleForAll
import cores.api.ImportantFunctions.setLevelAll
import cores.api.Messages.gameStartInXSecond
import cores.api.Messages.gameStartInXSecondTitle
import cores.api.Messages.gameStartTitle
import cores.api.Messages.gameTitle
import cores.api.Messages.sendConsole
import cores.api.Messages.waitingForXPlayers
import org.bukkit.Bukkit

class LobbyIdle : Countdown() {



    override fun start() {
        sendConsole("Idle test")
        if (!isIdling) {
            isIdling = true
            sendConsole("idlestartet")
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