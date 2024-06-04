package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst.BLUE_CORE_BACK
import cores.api.GlobalConst.BLUE_CORE_FRONT
import cores.api.GlobalConst.BLUE_CORE_LEFT
import cores.api.GlobalConst.BLUE_CORE_RIGHT
import cores.api.GlobalConst.RED_CORE_BACK
import cores.api.GlobalConst.RED_CORE_FRONT
import cores.api.GlobalConst.RED_CORE_LEFT
import cores.api.GlobalConst.RED_CORE_RIGHT
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalVars.PLAYERS
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.*
import kotlin.math.abs

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


    private val redCoreLocations = arrayOf(RED_CORE_FRONT, RED_CORE_BACK, RED_CORE_LEFT, RED_CORE_RIGHT)
    private val blueCoreLocations = arrayOf(BLUE_CORE_FRONT, BLUE_CORE_BACK, BLUE_CORE_LEFT, BLUE_CORE_RIGHT)
    private val coreRadius = 1 // Radius around core where blocks can be placed
    private val spawnRadius = 1 // Radius around spawn where blocks can be placed

    @EventHandler
    fun onPlayerBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val placedBlockLocation = event.block.location
        val playerTeam = plugin.teamHelper.getPlayerTeam(player)
        val ownCoreLocations = if (playerTeam == Team.RED) redCoreLocations else blueCoreLocations
        val ownSpawnLocation = if (playerTeam == Team.RED) TEAM_SPAWN_RED_LOCATION else TEAM_SPAWN_BLUE_LOCATION
        val enemySpawnLocation = if (playerTeam == Team.RED) TEAM_SPAWN_BLUE_LOCATION else TEAM_SPAWN_RED_LOCATION

        // Check if block is placed near own core locations
        for (coreLocation in ownCoreLocations) {
            if (isInRadius(placedBlockLocation, coreLocation, coreRadius)) {
                event.isCancelled = true
                player.sendMessage("You cannot place blocks near your own core.")
                return
            }
        }

        // Check if block is placed near own or enemy spawn location
        if (isInRadius(placedBlockLocation, ownSpawnLocation, spawnRadius)) {
            event.isCancelled = true
            player.sendMessage("You cannot place blocks near your own spawn.")
            return
        }

        if (isInRadius(placedBlockLocation, enemySpawnLocation, spawnRadius)) {
            event.isCancelled = true
            player.sendMessage("You cannot place blocks near the enemy spawn.")
            return
        }
    }

    private fun isInRadius(blockLocation: Location, centerLocation: Location, radius: Int): Boolean {
        val distanceX = abs(blockLocation.blockX - centerLocation.blockX)
        val distanceY = abs(blockLocation.blockY - centerLocation.blockY)
        val distanceZ = abs(blockLocation.blockZ - centerLocation.blockZ)
        return distanceX <= radius && distanceY <= radius && distanceZ <= radius
    }

}