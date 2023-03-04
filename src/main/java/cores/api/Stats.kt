package cores.api

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Stats {
    val playerStats = ArrayList<PlayerLocalStats>()
    fun addPlayersToStats() {
        Bukkit.getOnlinePlayers().forEach {
            playerStats.add(PlayerLocalStats(it))
        }
    }
    fun addKill(p: Player) {
        if(playerStats.contains(PlayerLocalStats(p))) {

        }
    }
}

class PlayerLocalStats(val p: Player) {
    var kills = 0
    var deaths = 0
    var wins = 0
    var blocksPlaced = 0
    var blocksDestroyed = 0
    var coresDestroyed = 0
}