package cores.api

import cores.Main.Companion.plugin
import org.bukkit.Bukkit

class StatsManager {
    val playerStats = mutableMapOf<String, PlayerStats>()

    fun initializePlayerStats() {
        Bukkit.getOnlinePlayers().forEach { player ->
            playerStats[player.name] = PlayerStats()
            addGame(player.name)
        }
    }

    fun addKill(playerName: String) {
        playerStats[playerName]?.let { it.kills += 1 }
    }

    fun addDeath(playerName: String) {
        playerStats[playerName]?.let { it.deaths += 1 }
    }

    fun addGame(playerName: String) {
        playerStats[playerName]?.let { it.games += 1 }
    }

    fun addWins(winnerTeam: Team) {
        if (winnerTeam == Team.RED) {
            plugin.teamHelper.teamRedPlayers.forEach { player ->
                playerStats[player.name]?.let { it.wins += 1 }
            }
        } else {
            plugin.teamHelper.teamBluePlayers.forEach { player ->
                playerStats[player.name]?.let { it.wins += 1 }
            }
        }

    }

    fun adBlocksPlaced(playerName: String) {
        playerStats[playerName]?.let { it.blocksPlaced += 1 }
    }

    fun addBlocksDestroyed(playerName: String) {
        playerStats[playerName]?.let { it.blocksDestroyed += 1 }
    }

    fun addCoresDestroyed(playerName: String) {
        playerStats[playerName]?.let { it.coresDestroyed += 1 }
    }

    fun getStats(playerName: String): PlayerStats? {
        return playerStats[playerName]
    }

    fun getKD(playerName: String): String {
        val stats = playerStats[playerName]
        if (stats != null) {
            return if (stats.deaths != 0) {
                val kdRatio = stats.kills.toDouble() / stats.deaths.toDouble()
                when {
                    kdRatio == Double.POSITIVE_INFINITY -> "∞" // Falls unendlich, gib "∞" zurück
                    kdRatio % 1.0 == 0.0 -> kdRatio.toInt()
                        .toString() // Falls eine ganze Zahl, entferne Nachkommastellen
                    else -> "%.2f".format(kdRatio) // Andernfalls gib das KD-Verhältnis auf zwei Nachkommastellen gerundet zurück
                }
            } else {
                stats.kills.toString() // Falls keine Tode vorhanden sind, gib die Anzahl der Kills zurück
            }
        }
        return "0" // Falls der Spieler nicht gefunden wurde, gib "0" zurück
    }
}

data class PlayerStats(
    var kills: Int = 0,
    var deaths: Int = 0,
    var games: Int = 0,
    var wins: Int = 0,
    var blocksPlaced: Int = 0,
    var blocksDestroyed: Int = 0,
    var coresDestroyed: Int = 0
)