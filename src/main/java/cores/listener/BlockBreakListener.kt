package cores.listener

import cores.Main.Companion.gameStateManager
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener: Listener {
    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        when(gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
            }
            GameStates.INGAME_STATE -> {
                if(PLAYERS.contains(e.player)) {
                    if(e.block.type == Material.BEACON) {

                    } else return
                } else {
                    e.isCancelled = true
                }
            }
            GameStates.END_STATE -> {
                e.isCancelled = true
            }
        }
    }
}