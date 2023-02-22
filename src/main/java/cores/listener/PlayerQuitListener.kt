package cores.listener

import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.playerLeftGame
import cores.gameStates.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {
    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        e.quitMessage = null
        when (CURRENT_GAME_STATE) {
            GameState.LOBBY_STATE -> {
                PLAYERS.remove(e.player)
                playerLeftGame(e.player.name)
            }
            GameState.INGAME_STATE -> {
                playerLeftGame(e.player.name)
                //TODO IngameState.....isGameOver()
            }
            GameState.END_STATE -> {
                playerLeftGame(e.player.name)
            }
        }
    }
}