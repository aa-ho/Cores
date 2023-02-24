package cores.listener

import cores.Main
import cores.Main.Companion.plugin
import cores.api.GlobalConst
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages
import cores.api.Messages.playerJoinedGame
import cores.api.Messages.playerRejoinedGame
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.joinMessage = null
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.player.teleport(LOBBY_SPAWN_LOCATION)
                PLAYERS[e.player] = true
                playerJoinedGame(e.player.name)
                if (PLAYERS.size < MIN_PLAYERS) {
                    Messages.waitingForXPlayers(MIN_PLAYERS - PLAYERS.size)
                } else {
                    plugin.gameStateManager.lobbyState.lobbyIdle.stop()
                }
            }
            GameStates.INGAME_STATE -> {
                if (PLAYERS.contains(e.player)) {
                    PLAYERS[e.player] = true
                    playerRejoinedGame(e.player.name)
                }
            }
            GameStates.END_STATE -> {

            }
        }
    }
}