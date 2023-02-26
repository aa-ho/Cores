package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.PREFIX_COLORED
import cores.api.Messages.sendPlayer
import cores.gameStates.GameStates
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    if (e.block.type == Material.BEACON) {
                        sendPlayer(e.player, PREFIX_COLORED)
                    } else return
                } else {
                    e.isCancelled = true
                }
            }
        }
    }
}