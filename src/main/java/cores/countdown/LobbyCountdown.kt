package cores.countdown

import cores.Main.Companion.gameStateManager
import cores.Main.Companion.plugin
import cores.api.GlobalConst.GAME_START_SOUND
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SECONDS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SOUND
import cores.api.GlobalVars.GAME_STARTING
import cores.api.ImportantFunctions
import cores.api.ImportantFunctions.disEnchantStartItem
import cores.api.ImportantFunctions.setLevelAll
import cores.api.Messages
import cores.api.Messages.gameStartInXSecond
import cores.api.Messages.sendConsole
import cores.gameStates.GameStates
import org.bukkit.Bukkit

class LobbyCountdown: Countdown() {
    var seconds = LOBBY_COUNTDOWN_SECONDS
    override fun start() {
        if(!isIdling) {
            isIdling = true

            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                setLevelAll(seconds)
                when (seconds) {
                    60, 30, 20, 10 -> {
                        ImportantFunctions.playSoundForAll(LOBBY_COUNTDOWN_SOUND)
                        ImportantFunctions.sendTitleForAll(Messages.gameStartInXSecondTitle(seconds), 0, 20, 0)
                        gameStartInXSecond(seconds)
                    }
                    8 -> {
                        ImportantFunctions.sendTitleForAll(Messages.gameTitle(), 15, 20, 15)
                    }
                    5, 4, 3, 2, 1 -> {
                        ImportantFunctions.playSoundForAll(LOBBY_COUNTDOWN_SOUND)
                        ImportantFunctions.sendTitleForAll(Messages.gameStartInXSecondTitle(seconds), 0, 20, 0)

                    }
                    0 -> {
                        ImportantFunctions.playSoundForAll(GAME_START_SOUND)
                        ImportantFunctions.sendTitleForAll(Messages.gameStartTitle(), 0, 20, 0)
                        stop()
                        gameStateManager.setGameState(GameStates.INGAME_STATE)
                    }
                }
                if(seconds == 10) {
                    disEnchantStartItem()
                }
                if (seconds < 3) {
                    GAME_STARTING = true
                }
                seconds--
                sendConsole(seconds.toString())
            }, 0, 20)
        }
    }

    override fun stop() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(taskID)
            isIdling = false
            GAME_STARTING = false
            seconds = LOBBY_COUNTDOWN_SECONDS
            disEnchantStartItem()
            setLevelAll(0)
        }
    }
}