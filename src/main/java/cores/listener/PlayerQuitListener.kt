package cores.listener


import cores.Main.Companion.plugin
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.updateLobbyScoreboardAll
import cores.api.Messages.playerLeftGame
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {
    @EventHandler
    fun quit(e: PlayerQuitEvent) {
        e.quitMessage = null
        e.player.inventory.clear()
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                PLAYERS.remove(e.player)
                plugin.rankHelper.removePlayersRank(e.player)
                playerLeftGame(e.player)
                plugin.teamHelper.leaveTeam(e.player)
                plugin.teamHelper.reopenTeamInventoryAll()
                if(PLAYERS.size < MIN_PLAYERS) {
                    plugin.gameStateManager.lobbyState.lobbyCountdown.stop()
                    plugin.gameStateManager.lobbyState.lobbyIdle.start()
                }
                updateLobbyScoreboardAll()
            }
            GameStates.INGAME_STATE -> {
                PLAYERS[e.player] = false
                playerLeftGame(e.player)
                plugin.gameStateManager.ingameState.gameOver()
            }
            GameStates.END_STATE -> playerLeftGame(e.player)
        }
    }
}