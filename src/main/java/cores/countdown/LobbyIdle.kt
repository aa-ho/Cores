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
import cores.api.Messages.waitingForXPlayers
import org.bukkit.Bukkit

class LobbyIdle : Countdown() {

    private var seconds = LOBBY_COUNTDOWN_SECONDS

    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                if (PLAYERS.isNotEmpty()) {
                    if (PLAYERS.size < MIN_PLAYERS) {
                        waitingForXPlayers(MIN_PLAYERS - PLAYERS.size)
                    } else {
                        setLevelAll(seconds)
                        when (seconds) {
                            60, 30, 20, 10, 5, 4, 3, 2, 1 -> {
                                playSoundForAll(LOBBY_COUNTDOWN_SOUND)
                                sendTitleForAll(gameStartInXSecondTitle(seconds), 0, 20, 0)
                                gameStartInXSecond(seconds)
                            }
                            8 -> {
                                sendTitleForAll(gameTitle(), 15, 20, 15)
                            }
                            0 -> {
                                playSoundForAll(GAME_START_SOUND)
                                sendTitleForAll(gameStartTitle(), 0, 20, 0)
                                stop()
                            }
                        }
                        if (seconds < 3) {
                            GAME_STARTING = true
                        }
                        seconds--
                    }
                }
            }, 0, 20)
        }
    }

    override fun stop() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(taskID)
            isIdling = false
            GAME_STARTING = false
        }
    }
}