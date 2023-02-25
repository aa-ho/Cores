package cores.api

import cores.Main
import cores.Main.Companion.teamHelper
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.blueTeamItem
import cores.api.GlobalConst.redTeamItem
import cores.api.GlobalConst.TEAM_SELECTOR_INVENTORY
import cores.api.GlobalConst.TEAMSELECTORINVENTORYTITLE
import cores.api.ImportantFunctions.sendPlayerFailedSound
import cores.api.Messages.sendPlayerJoinedTeam
import cores.api.Messages.sendPlayerTeamFull
import cores.api.Messages.teamSelectItems
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class TeamHelper {

    private val teamRedPlayers = ArrayList<Player>(MAX_PLAYERS/2)
    private val teamBluePlayers = ArrayList<Player>(MAX_PLAYERS/2)

    private fun isTeamFull(teams: Teams): Boolean {
        return if(teams == Teams.RED) teamRedPlayers.size >= MAX_PLAYERS/2
         else teamBluePlayers.size >= MAX_PLAYERS/2
    }
    fun teamSize(team: Teams): Int {
        return if(team == Teams.RED) teamRedPlayers.size
        else teamBluePlayers.size
    }
    fun getPlayerTeam(p: Player): Teams {
        return if(teamRedPlayers.contains(p)) Teams.RED
        else Teams.BLUE
    }

    fun joinTeam(p: Player, teams: Teams) {
        if(teams == Teams.RED) {
            if(isTeamFull(Teams.RED)) {
                sendPlayerTeamFull(p, Teams.RED)
                sendPlayerFailedSound(p)
            } else {
                leaveTeam(p)
                teamRedPlayers.add(p)
                p.openInventory.close()
                teamHelper.reopenTeamInventoryAll()
                sendPlayerJoinedTeam(p, Teams.RED)
            }
        } else {
            if(isTeamFull(Teams.BLUE)) {
                sendPlayerTeamFull(p, Teams.BLUE)
                sendPlayerFailedSound(p)
            } else {
                leaveTeam(p)
                teamBluePlayers.add(p)
                p.openInventory.close()
               teamHelper.reopenTeamInventoryAll()
                sendPlayerJoinedTeam(p, Teams.BLUE)
            }
        }

    }
    fun leaveTeam(p: Player) {
        val team: Teams?
        redTeamItem.lore = arrayListOf(teamSelectItems(Teams.RED))
        blueTeamItem.lore = arrayListOf(teamSelectItems(Teams.BLUE))
        if(isPlayerInTeam(p)) team = getPlayerTeam(p)
        else return
        if(team == Teams.RED) {
            if(teamRedPlayers.contains(p)) {
                teamRedPlayers.remove(p)
                teamHelper.reopenTeamInventoryAll()
            }
        } else {
            if(teamBluePlayers.contains(p)) {
                teamBluePlayers.remove(p)
                teamHelper.reopenTeamInventoryAll()
            }
        }
    }
    fun isPlayerInTeam(p: Player): Boolean {
        return !(teamRedPlayers.contains(p) || teamBluePlayers.contains(p))
    }

    fun buildTeamInventory() {
        TEAM_SELECTOR_INVENTORY.setItem(3, redTeamItem)
        TEAM_SELECTOR_INVENTORY.setItem(3, blueTeamItem)
    }
    fun openTeamInventory(p: Player, playSound: Boolean = true) {
        p.openInventory(TEAM_SELECTOR_INVENTORY)
        if(playSound) p.playSound(p.location, Sound.BLOCK_SHULKER_BOX_OPEN, 1.0F, 1.0F)
    }
    fun reopenTeamInventoryAll() {
        Bukkit.getOnlinePlayers().forEach {
            if(it.openInventory.title == TEAMSELECTORINVENTORYTITLE) {
                it.openInventory.close()
                openTeamInventory(it, false)
            }
        }
    }
}