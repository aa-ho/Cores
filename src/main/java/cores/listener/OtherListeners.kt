package cores.listener

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerEditBookEvent
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerPickupItemEvent

class OtherListeners: Listener {
    @EventHandler
    fun playerInteractAtEntity(e: PlayerInteractAtEntityEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun playerBedEnter(e: PlayerBedEnterEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun player(e: PlayerBucketFillEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun playerDropItem(e: PlayerDropItemEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun playerEditBook(e: PlayerEditBookEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun playerEvent(e: PlayerInteractEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun playerLaunchProjectile(e: PlayerLaunchProjectileEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun entityShootBow(e: EntityShootBowEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun entityPickupItem(e: EntityPickupItemEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun entityDamage(e: EntityDamageEvent) {
        e.isCancelled = true
    }
}