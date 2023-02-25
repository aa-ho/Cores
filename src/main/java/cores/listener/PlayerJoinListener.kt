package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions
import cores.api.ImportantFunctions.enchantStartItem
import cores.api.ImportantFunctions.setLobbyInventoryAndPrivileges
import cores.api.Messages
import cores.api.Messages.playerJoinedGame
import cores.api.Messages.playerRejoinedGame
import cores.api.Scoreboard
import cores.gameStates.GameStates
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.joinMessage = null
        Scoreboard().createScoreboard()
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.player.teleport(LOBBY_SPAWN_LOCATION)
                setLobbyInventoryAndPrivileges(e.player)
                e.player.gameMode = GameMode.ADVENTURE
                e.player.inventory.heldItemSlot = 0
                e.player.level = 0
                PLAYERS[e.player] = true
                playerJoinedGame(e.player.name)
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