package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.blueTeamItem
import cores.api.GlobalConst.redTeamItem
import cores.api.GlobalConst.TEAM_SELECTOR_INVENTORY
import cores.api.GlobalConst.TEAM_SELECTOR_INVENTORY_TITLE
import cores.api.GlobalConst.randomTeamItem
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.sendPlayerFailedSound
import cores.api.ImportantFunctions.setPlayerTeamActionBar
import cores.api.Messages.sendPlayerAlreadyInTeam
import cores.api.Messages.sendPlayerAlreadyRandomTeam
import cores.api.Messages.sendPlayerJoinedTeam
import cores.api.Messages.sendPlayerRandomTeam
import cores.api.Messages.sendPlayerTeamFull
import cores.api.Messages.teamSelectItems
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class TeamHelper {

    private val teamRedPlayers = ArrayList<Player>()
    private val teamBluePlayers = ArrayList<Player>()

    private fun isTeamFull(teams: Team): Boolean {
        return if (teams == Team.RED) teamRedPlayers.size >= MAX_PLAYERS / 2
        else teamBluePlayers.size >= MAX_PLAYERS / 2
    }

    fun isPlayerInTeam(p: Player): Boolean {
        return teamRedPlayers.contains(p) || teamBluePlayers.contains(p)
    }

    fun teamSize(team: Team): Int {
        return if (team == Team.RED) teamRedPlayers.size
        else teamBluePlayers.size
    }

    fun getPlayerTeam(p: Player): Team {
        return if (teamRedPlayers.contains(p)) Team.RED
        else Team.BLUE
    }

    fun assignPlayers() {
        PLAYERS.forEach { (player, _) ->
            if (!teamRedPlayers.contains(player) && !teamBluePlayers.contains(player)) {
                val randomNumber = Math.random()
                if (randomNumber < 0.5) {
                    teamRedPlayers.add(player)
                } else {
                    teamBluePlayers.add(player)
                }
            }
        }
    }

    fun joinTeam(p: Player, team: Team) {
        if (isPlayerInTeam(p) && getPlayerTeam(p) == team) {
            sendPlayerAlreadyInTeam(p, team)
            sendPlayerFailedSound(p)
        } else {
            if (team == Team.RED) {
                if (isTeamFull(Team.RED)) {
                    sendPlayerTeamFull(p, Team.RED)
                    sendPlayerFailedSound(p)
                } else {
                    leaveTeam(p)
                    teamRedPlayers.add(p)
                    p.openInventory.close()
                    plugin.teamHelper.reopenTeamInventoryAll()
                    sendPlayerJoinedTeam(p, Team.RED)
                    plugin.scoreboard.updateLobbyScoreboard(p)
                    setPlayerTeamActionBar(p)
                }
            } else {
                if (isTeamFull(Team.BLUE)) {
                    sendPlayerTeamFull(p, Team.BLUE)
                    sendPlayerFailedSound(p)
                } else {
                    leaveTeam(p)
                    teamBluePlayers.add(p)
                    p.openInventory.close()
                    plugin.teamHelper.reopenTeamInventoryAll()
                    sendPlayerJoinedTeam(p, Team.BLUE)
                    plugin.scoreboard.updateLobbyScoreboard(p)
                    setPlayerTeamActionBar(p)
                }
            }
        }
    }

    fun leaveTeam(p: Player) {
        val team: Team?
        if (isPlayerInTeam(p)) {
            team = getPlayerTeam(p)
            if (team == Team.RED) {
                if (teamRedPlayers.contains(p)) {
                    teamRedPlayers.remove(p)
                    plugin.teamHelper.reopenTeamInventoryAll()
                }
            } else {
                if (teamBluePlayers.contains(p)) {
                    teamBluePlayers.remove(p)
                    plugin.teamHelper.reopenTeamInventoryAll()
                }
            }
            redTeamItem.lore = arrayListOf(teamSelectItems(Team.RED))
            blueTeamItem.lore = arrayListOf(teamSelectItems(Team.BLUE))
        }
    }

    fun willPutPlayerInRandomTeam(p: Player) {
        if (!isPlayerInTeam(p)) {
            sendPlayerAlreadyRandomTeam(p)
            sendPlayerFailedSound(p)
        } else {
            leaveTeam(p)
            sendPlayerRandomTeam(p)
            p.openInventory.close()
        }
    }

    fun buildTeamInventory() {
        TEAM_SELECTOR_INVENTORY.setItem(2, redTeamItem)
        TEAM_SELECTOR_INVENTORY.setItem(6, blueTeamItem)
        TEAM_SELECTOR_INVENTORY.setItem(4, randomTeamItem)
    }

    fun openTeamInventory(p: Player, playSound: Boolean = true) {
        p.openInventory(TEAM_SELECTOR_INVENTORY)
        if (playSound) p.playSound(p.location, Sound.BLOCK_SHULKER_BOX_OPEN, 1.0F, 1.0F)
    }

    fun reopenTeamInventoryAll() {
        Bukkit.getOnlinePlayers().forEach {
            if (it.openInventory.title == TEAM_SELECTOR_INVENTORY_TITLE) {
                it.openInventory.close()
                openTeamInventory(it, false)
            }
        }
    }
}