package cores.countdown

import cores.Main.Companion.plugin
import cores.api.ImportantFunctions.setAllPlayerTeamActionBar
import org.bukkit.Bukkit

//TODO Don't like that...
class ActionBarIdle: Countdown() {
    override fun start() {
        if(!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                setAllPlayerTeamActionBar()
            }, 0, 20)
        }
    }

    override fun stop() {
        if(isIdling) {
            isIdling = false
            Bukkit.getScheduler().cancelTask(taskID)
        }
    }
}