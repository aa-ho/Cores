package cores.listener

import cores.Main
import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener : Listener {
    @EventHandler
    fun entityDamageByEntity(e: EntityDamageByEntityEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> e.isCancelled = true

            GameStates.END_STATE -> e.isCancelled = true
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.damager)) {
                    val attacker = e.damager as Player
                    val target = e.entity as Player
                    if (plugin.teamHelper.getPlayerTeam(attacker) == plugin.teamHelper.getPlayerTeam(target)) {
                        e.isCancelled = true
                    } else return
                } else e.isCancelled = true
            }
        }
    }
}