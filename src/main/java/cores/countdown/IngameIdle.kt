package cores.countdown

import cores.Main.Companion.plugin
import cores.api.Beacon
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.alarmForTeam
import cores.api.ImportantFunctions.giveSlowMiningToTeam
import cores.api.Team
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class InGameIdle : Countdown() {

    private fun checkNearbyPlayersForCore(cores: Map<Beacon, Location>, team: Team, enemyTeam: Team) {
        cores.forEach { (key, value) ->
            for (entity in value.world.getNearbyEntities(value, 5.0, 5.0, 5.0)) {
                if (entity.type == EntityType.PLAYER) {
                    val player = entity as Player
                    if (PLAYERS.containsKey(player) && plugin.teamHelper.getPlayerTeam(player) != team) {
                        alarmForTeam(team, key)
                        giveSlowMiningToTeam(enemyTeam)
                    }
                }
            }
        }
    }

    override fun start() {
        if (!isIdling) {
            isIdling = true
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                checkNearbyPlayersForCore(plugin.beaconHelper.redCores, Team.RED, Team.BLUE)
                checkNearbyPlayersForCore(plugin.beaconHelper.blueCores, Team.BLUE, Team.RED)
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