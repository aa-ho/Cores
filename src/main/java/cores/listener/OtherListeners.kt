package cores.listener

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import cores.Main
import cores.Main.Companion.gameStateManager
import cores.Main.Companion.teamHelper
import cores.api.GlobalConst.LEAVE_GAME_ITEM_NAME
import cores.api.GlobalConst.START_GAME_ITEM_NAME
import cores.api.GlobalConst.TEAM_SELECTOR_ITEM_NAME
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions
import cores.api.ImportantFunctions.kickPlayerLeave
import cores.api.ImportantFunctions.setIngamePlayerItems
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.sendPlayer
import cores.api.Teams
import cores.gameStates.GameStates
import io.papermc.paper.event.player.PlayerItemCooldownEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
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
        if(e.whoClicked !is Player) return
        val p = e.whoClicked as Player
        if(gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE && !PLAYERS.contains(p)) {
            e.isCancelled = true
            if(e.currentItem == null) return
            if(e.currentItem!!.itemMeta == null) return
        } else {
            e.isCancelled = true
            if(e.currentItem == null) return
            if(e.currentItem!!.itemMeta == null) return
            when(e.currentItem!!.itemMeta.displayName) {
                RED_COLORED -> {
                    teamHelper.joinTeam(p, Teams.RED)
                }
                BLUE_COLORED -> {
                    teamHelper.joinTeam(p, Teams.BLUE)
                }
            }
        }
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
        when (gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
                if(itemCoolDown.contains(e.player.name)) return
                addPlayerToItemCoolDown(e.player.name)
                if (e.player.inventory.itemInMainHand.itemMeta== null || !e.player.inventory.itemInMainHand.itemMeta.hasDisplayName()) return
                if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK
                    || e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK
                ) {
                    when(e.player.inventory.itemInMainHand.itemMeta.displayName) {
                        START_GAME_ITEM_NAME -> {
                            ImportantFunctions.skipCountdown(e.player)
                        }
                        LEAVE_GAME_ITEM_NAME -> {
                            kickPlayerLeave(e.player)
                        }
                        TEAM_SELECTOR_ITEM_NAME -> {
                            teamHelper.openTeamInventory(e.player)
                        }
                    }
                    sendPlayer(e.player, "test")
                }
            }
            GameStates.INGAME_STATE -> {
                if (!PLAYERS.contains(e.player)) {
                    e.isCancelled = true
                } else {
                    if(itemCoolDown.contains(e.player.name)) return
                    addPlayerToItemCoolDown(e.player.name)
                    //TODO Spectator stuff!
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
    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        when(gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
            }
            GameStates.INGAME_STATE -> {
                if(PLAYERS.contains(e.player)) {
                    e.player.inventory.clear()
                } else e.isCancelled = true
            }
            GameStates.END_STATE -> {
                e.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onRespawn(e: PlayerRespawnEvent) {
        when(gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
            }
            GameStates.INGAME_STATE -> {
                if(PLAYERS.contains(e.player)) {
                    setIngamePlayerItems(e.player)
                }
            }
            GameStates.END_STATE -> {
            }
        }

    }
}