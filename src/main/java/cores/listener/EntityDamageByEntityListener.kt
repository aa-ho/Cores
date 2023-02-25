package cores.listener

import cores.Main
import cores.Main.Companion.gameStateManager
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener: Listener {
    @EventHandler
    fun entityDamageByEntity(e: EntityDamageByEntityEvent) {
        when (gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
            }
            GameStates.INGAME_STATE -> {
                if (PLAYERS.contains(e.damager)) {

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