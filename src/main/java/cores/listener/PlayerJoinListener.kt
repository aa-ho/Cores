package cores.listener


import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.enchantStartItem
import cores.api.ImportantFunctions.setAllPlayerTeamActionBar
import cores.api.ImportantFunctions.setLobbyInventoryAndPrivileges
import cores.api.ImportantFunctions.updateScoreboardAll
import cores.api.Messages
import cores.api.Messages.sendPlayerJoinedGame
import cores.api.Messages.playerRejoinedGame
import cores.gameStates.GameStates
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.joinMessage = null
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                PLAYERS[e.player] = true
                e.player.teleport(LOBBY_SPAWN_LOCATION)
                e.player.inventory.clear()
                setLobbyInventoryAndPrivileges(e.player)
                plugin.teamHelper.reopenTeamInventoryAll()
                updateScoreboardAll()
                e.player.gameMode = GameMode.ADVENTURE
                e.player.inventory.heldItemSlot = 0
                e.player.level = 0
                sendPlayerJoinedGame(e.player.name)
                if (PLAYERS.size < MIN_PLAYERS) {
                    Messages.waitingForXPlayers(MIN_PLAYERS - PLAYERS.size)
                } else {
                    enchantStartItem()
                    plugin.gameStateManager.lobbyState.lobbyIdle.stop()
                }
            }
            GameStates.INGAME_STATE -> {
                if (PLAYERS.contains(e.player)) {
                    PLAYERS[e.player] = true
                    playerRejoinedGame(e.player.name)
                } else {
                    e.player.gameMode = GameMode.ADVENTURE
                }
            }
            GameStates.END_STATE -> {}
        }
    }
}