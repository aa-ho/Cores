package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.blueTeamItem
import cores.api.GlobalConst.redTeamItem
import cores.api.GlobalConst.TEAM_SELECTOR_INVENTORY
import cores.api.GlobalConst.TEAM_SELECTOR_INVENTORY_TITLE
import cores.api.GlobalConst.randomTeamItem
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

    private fun isTeamFull(teams: Teams): Boolean {
        return if (teams == Teams.RED) teamRedPlayers.size >= MAX_PLAYERS / 2
        else teamBluePlayers.size >= MAX_PLAYERS / 2
    }

    fun isPlayerInTeam(p: Player): Boolean {
        return teamRedPlayers.contains(p) || teamBluePlayers.contains(p)
    }

    fun teamSize(team: Teams): Int {
        return if (team == Teams.RED) teamRedPlayers.size
        else teamBluePlayers.size
    }

    fun getPlayerTeam(p: Player): Teams {
        return if (teamRedPlayers.contains(p)) Teams.RED
        else Teams.BLUE
    }

    fun joinTeam(p: Player, team: Teams) {
        if (isPlayerInTeam(p) && getPlayerTeam(p) == team) {
            sendPlayerAlreadyInTeam(p, team)
            sendPlayerFailedSound(p)
        } else {
            if (team == Teams.RED) {
                if (isTeamFull(Teams.RED)) {
                    sendPlayerTeamFull(p, Teams.RED)
                    sendPlayerFailedSound(p)
                } else {
                    leaveTeam(p)
                    teamRedPlayers.add(p)
                    p.openInventory.close()
                    plugin.teamHelper.reopenTeamInventoryAll()
                    sendPlayerJoinedTeam(p, Teams.RED)
                    plugin.scoreboard.setLobbyScoreboard(p)
                    setPlayerTeamActionBar(p)
                }
            } else {
                if (isTeamFull(Teams.BLUE)) {
                    sendPlayerTeamFull(p, Teams.BLUE)
                    sendPlayerFailedSound(p)
                } else {
                    leaveTeam(p)
                    teamBluePlayers.add(p)
                    p.openInventory.close()
                    plugin.teamHelper.reopenTeamInventoryAll()
                    sendPlayerJoinedTeam(p, Teams.BLUE)
                    plugin.scoreboard.setLobbyScoreboard(p)
                    setPlayerTeamActionBar(p)
                }
            }
        }
    }

    fun leaveTeam(p: Player) {
        val team: Teams?
        if (isPlayerInTeam(p)) {
            team = getPlayerTeam(p)
            if (team == Teams.RED) {
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
            redTeamItem.lore = arrayListOf(teamSelectItems(Teams.RED))
            blueTeamItem.lore = arrayListOf(teamSelectItems(Teams.BLUE))
        }
    }
    fun willPutPlayerInRandomTeam(p: Player) {
        if(!isPlayerInTeam(p)) {
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