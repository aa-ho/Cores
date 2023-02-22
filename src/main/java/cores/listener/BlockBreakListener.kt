package cores.listener

import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameState
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener: Listener {
    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        when(CURRENT_GAME_STATE) {
            GameState.LOBBY_STATE -> {
                e.isCancelled = true
            }
            GameState.INGAME_STATE -> {
                if(PLAYERS.contains(e.player)) {
                    if(e.block.type == Material.BEACON) {
                        //TODO CHECK WHICH BEACON
                    } else return
                } else {
                    e.isCancelled = true
                }
            }
            GameState.END_STATE -> {
                e.isCancelled = true
            }
        }
    }
}