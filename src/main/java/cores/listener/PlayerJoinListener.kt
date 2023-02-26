package cores.listener


import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalConst.SPECTATOR_SPAWN_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.enchantStartItem
import cores.api.ImportantFunctions.setLobbyInventoryAndPrivileges
import cores.api.ImportantFunctions.updateLobbyScoreboardAll
import cores.api.Messages
import cores.api.Messages.playerRejoinedGame
import cores.api.Messages.sendPlayerJoinedGame
import cores.api.Permission
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.permissions.PermissionAttachment
import java.util.*


class PlayerJoinListener : Listener {


    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.joinMessage = null
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                PLAYERS[e.player] = true
                //TODO REMOVE!
                val perms = HashMap<UUID, PermissionAttachment>()
                val attachment: PermissionAttachment = e.player.addAttachment(plugin)
                perms[e.player.uniqueId] = attachment
                val pperms = perms[e.player.uniqueId]
                pperms!!.setPermission(Permission.DEFAULT.permission, true)
                plugin.rankHelper.setPlayersRank(e.player)
                e.player.teleport(LOBBY_SPAWN_LOCATION)
                e.player.inventory.clear()
                setLobbyInventoryAndPrivileges(e.player)
                plugin.teamHelper.reopenTeamInventoryAll()
                updateLobbyScoreboardAll()
                e.player.gameMode = GameMode.ADVENTURE
                e.player.inventory.heldItemSlot = 0
                e.player.level = 0
                sendPlayerJoinedGame(e.player)
                if (PLAYERS.size < MIN_PLAYERS) {
                    Messages.waitingForXPlayers(MIN_PLAYERS - PLAYERS.size)
                } else {
                    enchantStartItem()
                    plugin.gameStateManager.lobbyState.lobbyIdle.stop()
                }
            }
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    PLAYERS[e.player] = true
                    playerRejoinedGame(e.player)
                    e.player.gameMode = GameMode.SURVIVAL
                    e.player.inventory.clear()
                    e.player.level = 0
                    if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED)
                        e.player.teleport(TEAM_SPAWN_RED_LOCATION)
                    else e.player.teleport(TEAM_SPAWN_BLUE_LOCATION)
                } else {
                    e.player.gameMode = GameMode.ADVENTURE
                    e.player.teleport(SPECTATOR_SPAWN_LOCATION)
                }
            }
            GameStates.END_STATE -> {}
        }
    }
}