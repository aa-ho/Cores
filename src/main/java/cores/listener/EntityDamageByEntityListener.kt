package cores.listener

import cores.Main
import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.broadcastMessage
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.projectiles.ProjectileSource

class EntityDamageByEntityListener : Listener {
    @EventHandler
    fun entityDamageByEntity(e: EntityDamageByEntityEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
            GameStates.INGAME_STATE -> {
                if (e.damager.type == EntityType.PLAYER && e.entity.type == EntityType.PLAYER) {
                    if (PLAYERS.containsKey(e.damager) && PLAYERS.containsKey(e.entity)) {
                        val attacker = e.damager as Player
                        val target = e.entity as Player
                        if (plugin.teamHelper.getPlayerTeam(attacker) == plugin.teamHelper.getPlayerTeam(target))
                            e.isCancelled = true
                        else return
                    } else e.isCancelled = true
                } else if (e.damager.type == EntityType.ARROW) {
                    val arrow = e.damager as Arrow
                    val shooter = arrow.shooter as Player
                    if (e.entity.type != EntityType.PLAYER) return
                    if (plugin.teamHelper.getPlayerTeam(e.entity as Player) == plugin.teamHelper.getPlayerTeam(shooter))
                        e.isCancelled = true
                } else e.isCancelled = true
            }
        }
    }
}