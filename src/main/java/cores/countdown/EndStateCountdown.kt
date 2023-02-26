package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalConst.END_COUNTDOWN_SECONDS
import cores.api.ImportantFunctions
import cores.api.Messages.serverStopInXSeconds
import org.bukkit.Bukkit

class EndStateCountdown: Countdown() {

    private var seconds = END_COUNTDOWN_SECONDS

    override fun start() {
        if(!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                ImportantFunctions.setLevelAll(seconds)
                when(seconds) {
                    10, 5, 4, 3, 2, 1 -> {
                        serverStopInXSeconds(seconds)
                    }
                    0 -> {
                        stop()
                    }
                }
                seconds--

            }, 0, 10)
        }
    }

    override fun stop() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(taskID)
            isIdling = false
        }
    }
}