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

    private val quarterPercent = 0.25
    private val timePoints = listOf(0.8, 0.9, 0.95, 0.975, 0.99, 0.995)

    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                updateInGameScoreboardAll()
                when(seconds) {
                    INGAME_TOTAL_SECONDS/2 -> {
                        if(INGAME_TOTAL_SECONDS>120) {
                            halftimeBroadcast()
                            playTimeReminderSoundToAll()
                        }
                    }
/*                    (INGAME_TOTAL_SECONDS * quarterPercent).toInt() -> {
                        if (INGAME_TOTAL_SECONDS > 240) {
                            val minutesLeft = (INGAME_TOTAL_SECONDS * quarterPercent) / 60
                            gameEndsInXMinutes(minutesLeft.toInt())
                            playTimeReminderSoundToAll()
                        }
                    }
                    in timePoints.map { (it * INGAME_TOTAL_SECONDS).toInt() } -> {
                        val minutesLeft = seconds / 60
                        gameEndsInXMinutes(minutesLeft)
                        playTimeReminderSoundToAll()
                    }*/
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