package cores.listener

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import cores.Main
import cores.api.GlobalConst.LEAVE_GAME_ITEM_TITLE
import cores.api.GlobalConst.START_GAME_ITEM_TITLE
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions
import cores.api.ImportantFunctions.kickPlayerLeave
import cores.api.Messages.sendPlayer
import cores.gameStates.GameStates
import io.papermc.paper.event.player.PlayerItemCooldownEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.*

class OtherListeners : Listener {
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
    fun inventoryClick(e: InventoryClickEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun inv(e: InventoryMoveItemEvent) {
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

    private val itemCoolDown = arrayListOf<String>()
    private fun addPlayerToItemCoolDown(name: String) {
        itemCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, {
            itemCoolDown.remove(name)
        }, 20)
    }
    @EventHandler
    fun playerInteractEvent(e: PlayerInteractEvent) {
        when (Main.plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
                if(itemCoolDown.contains(e.player.name)) return
                addPlayerToItemCoolDown(e.player.name)
                if (e.player.inventory.itemInMainHand.itemMeta== null || !e.player.inventory.itemInMainHand.itemMeta.hasDisplayName()) return
                if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK
                    || e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK
                ) {
                    when(e.player.inventory.itemInMainHand.itemMeta.displayName) {
                        START_GAME_ITEM_TITLE -> {
                            ImportantFunctions.skipCountdown(e.player)
                            //createSmokeCircle(e.player) //TODO Weg damit!
                        }
                        LEAVE_GAME_ITEM_TITLE -> {
                            kickPlayerLeave(e.player)
                        }
                    }
                    sendPlayer(e.player, "test")
                }
            }
            GameStates.INGAME_STATE -> {
                if (!PLAYERS.contains(e.player)) {
                    e.isCancelled = true
                }
            }
            GameStates.END_STATE -> {
                e.isCancelled = true
            }
        }
        e.isCancelled = true
    }
    @EventHandler
    fun cancelEat(e: PlayerItemConsumeEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun coolDown(e: PlayerItemCooldownEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun swapItem(e: PlayerSwapHandItemsEvent) {
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