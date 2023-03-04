package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.ImportantFunctions.playTimeReminderSoundToAll
import cores.api.ImportantFunctions.updateInGameScoreboardAll
import cores.api.Messages.gameEndsInXMinutes
import cores.api.Messages.gameEndsInXSeconds
import cores.api.Messages.halftimeBroadcast
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcastMessage

class IngameTimer : Countdown() {

    var seconds = 0

    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                updateInGameScoreboardAll()
                when(seconds) {
                    INGAME_TOTAL_SECONDS/2 -> {
                        halftimeBroadcast()
                        playTimeReminderSoundToAll()
                    }
                    INGAME_TOTAL_SECONDS/4 -> {
                        gameEndsInXMinutes(INGAME_TOTAL_SECONDS/60/4)
                        playTimeReminderSoundToAll()
                    }
                    INGAME_TOTAL_SECONDS-1200, INGAME_TOTAL_SECONDS-600, INGAME_TOTAL_SECONDS-300, INGAME_TOTAL_SECONDS-240, INGAME_TOTAL_SECONDS-180, INGAME_TOTAL_SECONDS-120 -> {
                        gameEndsInXMinutes(seconds/60)
                        playTimeReminderSoundToAll()
                    }
                    INGAME_TOTAL_SECONDS-60, INGAME_TOTAL_SECONDS-30, INGAME_TOTAL_SECONDS-20, INGAME_TOTAL_SECONDS-10, INGAME_TOTAL_SECONDS-5, INGAME_TOTAL_SECONDS-4, INGAME_TOTAL_SECONDS-3, INGAME_TOTAL_SECONDS-2, INGAME_TOTAL_SECONDS-1 -> {
                        gameEndsInXSeconds(seconds)
                    }
                    INGAME_TOTAL_SECONDS -> {
                        stop()
                    }
                }
                seconds++
            }, 0, 20)
        }
    }

    override fun stop() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(taskID)
            isIdling = false
        }
    }
}