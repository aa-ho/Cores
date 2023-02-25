package cores.listener

import cores.Main.Companion.gameStateManager
import cores.Main.Companion.teamHelper
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.playerLeftGame
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {
    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        e.quitMessage = null
        when (gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                PLAYERS.remove(e.player)
                playerLeftGame(e.player.name)
                teamHelper.leaveTeam(e.player)
                teamHelper.reopenTeamInventoryAll()
                if(PLAYERS.size < MIN_PLAYERS) {
                    gameStateManager.lobbyState.lobbyCountdown.stop()
                    gameStateManager.lobbyState.lobbyIdle.start()
                }
            }
            GameStates.INGAME_STATE -> {
                PLAYERS[e.player] = false
                playerLeftGame(e.player.name)
                gameStateManager.ingameState.isGameOver()
            }
            GameStates.END_STATE -> {
                playerLeftGame(e.player.name)
            }
        }
    }
}