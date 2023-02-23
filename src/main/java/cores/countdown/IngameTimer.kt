package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.ImportantFunctions.playTimeReminderSoundToAll
import cores.api.Messages.gameEndsInXMinutes
import cores.api.Messages.gameEndsInXSeconds
import cores.api.Messages.halftimeBroadcast
import org.bukkit.Bukkit

class IngameTimer : Countdown() {

    private var seconds = INGAME_TOTAL_SECONDS

    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                when(seconds) {
                    INGAME_TOTAL_SECONDS/2 -> {
                        halftimeBroadcast()
                        playTimeReminderSoundToAll()
                    }
                    INGAME_TOTAL_SECONDS/4 -> {
                        gameEndsInXMinutes(INGAME_TOTAL_SECONDS/60/4)
                        playTimeReminderSoundToAll()
                    }
                    1200, 600, 300, 240, 180, 120 -> {
                        gameEndsInXMinutes(seconds/60)
                        playTimeReminderSoundToAll()
                    }
                    60, 30, 20, 10, 5, 4, 3, 2, 1 -> {
                        gameEndsInXSeconds(seconds)
                    }
                    0 -> {
                        stop()
                    }
                }
                seconds--
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