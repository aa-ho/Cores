package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.sendPlayerKillSound
import cores.api.ImportantFunctions.setIngamePlayerItems
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.RANDOM_TEAM_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.sendPlayerDied
import cores.api.Messages.sendPlayerKilledByPlayer
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcastMessage
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

class OtherListeners : Listener {
    /*    @EventHandler
        fun playerInteractAtEntity(e: PlayerInteractAtEntityEvent) {
            //e.isCancelled = true //TODO??
        }    @EventHandler
        fun inv(e: InventoryMoveItemEvent) {
            if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        }*//*
    private val itemCoolDown = arrayListOf<String>()
    private fun addPlayerToItemCoolDown(name: String) {
        itemCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            itemCoolDown.remove(name)
        }, 20)
    }
    @EventHandler
    fun playerInteractEvent(e: PlayerInteractEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                e.isCancelled = true
                if (itemCoolDown.contains(e.player.name)) return
                addPlayerToItemCoolDown(e.player.name)
                if (e.player.inventory.itemInMainHand.itemMeta == null || !e.player.inventory.itemInMainHand.itemMeta.hasDisplayName()) return
                if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK
                    || e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK
                ) {
                    when (e.player.inventory.itemInMainHand.itemMeta.displayName) {
                        START_GAME_ITEM_NAME -> {
                            ImportantFunctions.skipCountdown(e.player)
                        }
                        LEAVE_GAME_ITEM_NAME -> {
                            kickPlayerLeave(e.player)
                        }
                        TEAM_SELECTOR_ITEM_NAME -> {
                            plugin.teamHelper.openTeamInventory(e.player)
                        }
                    }
                }
            }
            GameStates.INGAME_STATE -> {
                if (!PLAYERS.containsKey(e.player)) {
                    e.isCancelled = true
                    if (itemCoolDown.contains(e.player.name)) return
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
*//*    @EventHandler
    fun cancelEat(e: PlayerItemConsumeEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
    }*//*
*//*    @EventHandler
    fun coolDown(e: PlayerItemCooldownEvent) {
        //TODO???
    }*//*
    @EventHandler
    fun entityDamage(e: EntityDamageEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (e.entity.type != EntityType.PLAYER) return
        if (!PLAYERS.containsKey(e.entity)) e.isCancelled = true
    }
*/
    @EventHandler
    fun playerEditBook(e: PlayerEditBookEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun eat(e: FoodLevelChangeEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (e.entity.type != EntityType.PLAYER) return
        if (!PLAYERS.containsKey(e.entity)) e.isCancelled = true
    }

    @EventHandler
    fun swapItem(e: PlayerSwapHandItemsEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (!PLAYERS.containsKey(e.player)) e.isCancelled = true
    }

    @EventHandler
    fun entityItem(e: EntityPickupItemEvent) {
        if (e.entity.type != EntityType.PLAYER) return
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (!PLAYERS.containsKey(e.entity)) e.isCancelled = true
    }

    @EventHandler
    fun playerBedEnter(e: PlayerBedEnterEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun player(e: PlayerBucketFillEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (PLAYERS.containsKey(e.player)) return
        e.isCancelled = true
    }

    @EventHandler
    fun playerDropItem(e: PlayerDropItemEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
    }

    @EventHandler
    fun respawn(e: PlayerRespawnEvent) {
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) {
            if (PLAYERS.containsKey(e.player)) {
                e.player.teleport(if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) TEAM_SPAWN_RED_LOCATION else TEAM_SPAWN_BLUE_LOCATION)
                setIngamePlayerItems(e.player)
            }
        }
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        e.deathMessage = null
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    e.drops.clear()
                    if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) e.player.teleport(TEAM_SPAWN_RED_LOCATION)
                    else e.player.teleport(TEAM_SPAWN_BLUE_LOCATION)
                    if (e.player.killer != null) {
                        sendPlayerKilledByPlayer(e.player, e.player.killer!!)
                        sendPlayerKillSound(e.player.killer!!)
                        //TODO update scoreboard?
                    } else {
                        //TODO mor reasons...
                        when (e.player.lastDamageCause) {
                            //TODO...
                        }
                        broadcastMessage(e.player.lastDamageCause.toString())
                        broadcastMessage(e.player.lastDamageCause?.cause.toString())
                        sendPlayerDied(e.player)
                    }
                    //e.isCancelled = true //TODO???
                    //e.player.spigot().respawn()
                    /*e.player.teleport(if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) TEAM_SPAWN_RED_LOCATION else TEAM_SPAWN_BLUE_LOCATION)
                    setIngamePlayerItems(e.player)*/
                } else e.isCancelled = true
            }
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
        }
    }

    private val itemClickCoolDown = arrayListOf<String>()
    private fun addPlayerToItemClickCoolDown(name: String) {
        itemClickCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            itemClickCoolDown.remove(name)
        }, 10)
    }

    @EventHandler
    fun inventoryClick(e: InventoryClickEvent) {
        if (e.whoClicked !is Player) return
        val p = e.whoClicked as Player
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) {
            if (PLAYERS.containsKey(p)) return
            e.isCancelled = true
            if (e.currentItem == null) return
            if (e.currentItem!!.itemMeta == null) return
        } else {
            e.isCancelled = true
            if (e.currentItem == null) return
            if (e.currentItem!!.itemMeta == null) return
            if (itemClickCoolDown.contains(p.name)) return
            addPlayerToItemClickCoolDown(p.name)
            when (e.currentItem!!.itemMeta.displayName) {
                RED_COLORED -> {
                    plugin.teamHelper.joinTeam(p, Team.RED)
                }
                BLUE_COLORED -> {
                    plugin.teamHelper.joinTeam(p, Team.BLUE)
                }
                RANDOM_TEAM_COLORED -> {
                    plugin.teamHelper.willPutPlayerInRandomTeam(p)
                }
            }
        }
    }
}