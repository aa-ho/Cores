package cores.listener

import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.playerJoinedGame
import cores.api.Messages.playerRejoinedGame
import cores.gameStates.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.joinMessage = null
        when (CURRENT_GAME_STATE) {
            GameState.LOBBY_STATE -> {
                PLAYERS.add(e.player)
                playerJoinedGame(e.player.name)
            }
            GameState.INGAME_STATE -> {
                if (PLAYERS.contains(e.player)) {
                    playerRejoinedGame(e.player.name)
                }
            }
            GameState.END_STATE -> {

            }
        }
    }
}