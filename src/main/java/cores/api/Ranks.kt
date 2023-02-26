package cores.api

import cores.Main.Companion.plugin
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

class RankHelper {
    init {
        registerTeams()
    }
    private val playerRanks = HashMap<String, Rank>()
    fun getPlayersRank(p: Player): Rank? = playerRanks[p.name]
    fun setPlayersRank(p: Player) {
        for(rank in Rank.values()) {
            if(p.hasPermission(rank.permission.permission)) {
                playerRanks[p.name] = rank
                //TODO fix scoreboard!
                //plugin.scoreboard.lobbyScoreboard.getTeam(rank.rankName)?.addPlayer(p)
                return
            }
        }
    }
    fun getPlayersRankColor(p: Player): String {
        return getPlayersRank(p)?.colorDisplayed ?: "§a"
    }

    fun removePlayersRank(p: Player) {
        if(playerRanks.contains(p.name)) {
            playerRanks.remove(p.name)
            //TODO dont like...
            //TODO fix scoreboard!
            //getPlayersRank(p)?.let { plugin.scoreboard.lobbyScoreboard.getTeam(it.rankName)?.removePlayer(p) }
        }
    }

    private fun registerTeams() {
        for(rank in Rank.values()) {
            ////TODO fix scoreboard!
            //val team = plugin.scoreboard.lobbyScoreboard.registerNewTeam(rank.rankName)
            //team.color(rank.color)
            //team.prefix = rank.prefix
        }
    }
}

enum class Rank(val rankName: String, val color: NamedTextColor, val colorDisplayed: String, val permission: Permission, val prefix: String) {
    ADMIN("Admin", NamedTextColor.DARK_AQUA, "§3", Permission.ADMIN, "§3Admin §8| §3 "), PLAYER("Player", NamedTextColor.GREEN, "§a", Permission.DEFAULT, "§a")
}

/*class PermissionHelper {
    fun getPlayersPermission(p: Player): Permission? {
        return plugin.rankHelper.getPlayerRank(p)?.permission
    }

    fun hasPlayerPermission(p: Player, permission: Permission): Boolean =
        plugin.rankHelper.getPlayerRank(p)!!.permission == permission
}*/

enum class Permission(val permission: String) {
    DEFAULT("rank.default"), ADMIN("rank.admin")
}
