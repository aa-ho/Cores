package cores.listener

import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameState
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener: Listener {
    fun entityDamageByEntity(e: EntityDamageByEntityEvent) {
        when (CURRENT_GAME_STATE) {
            GameState.LOBBY_STATE -> {
                e.isCancelled = true
            }
            GameState.INGAME_STATE -> {
                if (PLAYERS.contains(e.damager)) {

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