package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst.LEAVE_GAME_ITEM_NAME
import cores.api.GlobalConst.START_GAME_ITEM_NAME
import cores.api.GlobalConst.TEAM_SELECTOR_ITEM_NAME
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.kickPlayerLeave
import cores.api.ImportantFunctions.sendPlayerKillSound
import cores.api.ImportantFunctions.setIngamePlayerItems
import cores.api.ImportantFunctions.skipCountdown
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.RANDOM_TEAM_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.sendPlayerDied
import cores.api.Messages.sendPlayerKilledByPlayer
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcastMessage
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

class OtherListeners : Listener {
    @EventHandler
    fun entityDamage(e: EntityDamageEvent) {
        if (plugin.gameStateManager.getCurrentGameState() != GameStates.INGAME_STATE) e.isCancelled = true
        if (e.entity.type != EntityType.PLAYER) return
        if (!PLAYERS.containsKey(e.entity)) e.isCancelled = true
    }

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
}