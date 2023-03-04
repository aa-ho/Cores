package cores.listener


import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalConst.SPECTATOR_SPAWN_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.enchantStartItem
import cores.api.ImportantFunctions.resetPlayer
import cores.api.ImportantFunctions.setLobbyInventoryAndPrivileges
import cores.api.ImportantFunctions.updateLobbyScoreboardAll
import cores.api.Messages
import cores.api.Messages.broadcastPlayerRejoinedGame
import cores.api.Messages.broadcastPlayerJoinedGame
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
                resetPlayer(e.player)
                //e.player.isCollidable = false Not working?
                e.player.teleport(LOBBY_SPAWN_LOCATION)
                setLobbyInventoryAndPrivileges(e.player)
                plugin.teamHelper.reopenTeamInventoryAll()
                updateLobbyScoreboardAll()
                e.player.gameMode = GameMode.ADVENTURE
                broadcastPlayerJoinedGame(e.player)
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
                    resetPlayer(e.player)
                    broadcastPlayerRejoinedGame(e.player)
                    e.player.gameMode = GameMode.SURVIVAL
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