package cores.countdown

import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.alarmForTeam
import cores.api.ImportantFunctions.giveSlowMiningToTeam
import cores.api.Team
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class InGameIdle : Countdown() {
    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                plugin.beaconHelper.redCores.forEach {
                    for (entity in it.value.world.getNearbyEntities(it.value, 5.0, 5.0, 5.0)) {
                        if (entity.type == EntityType.PLAYER) {
                            val p = entity as Player
                            if (PLAYERS.containsKey(p)) {
                                if (plugin.teamHelper.getPlayerTeam(p) != Team.RED) {
                                    alarmForTeam(Team.RED, it.key)
                                    giveSlowMiningToTeam(Team.BLUE)
                                }
                            }
                        }
                    }
                }
                plugin.beaconHelper.blueCores.forEach {
                    for (entity in it.value.world.getNearbyEntities(it.value, 5.0, 5.0, 5.0)) {
                        if (entity.type == EntityType.PLAYER) {
                            val p = entity as Player
                            if (PLAYERS.containsKey(p)) {
                                if (plugin.teamHelper.getPlayerTeam(p) != Team.BLUE) {
                                    alarmForTeam(Team.BLUE, it.key)
                                    giveSlowMiningToTeam(Team.RED)
                                }
                            }
                        }
                    }
                }
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