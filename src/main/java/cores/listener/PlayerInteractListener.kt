package cores.listener

import cores.Main
import cores.api.GlobalConst
import cores.api.GlobalVars
import cores.api.ImportantFunctions
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener : Listener {

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
                if (itemCoolDown.contains(e.player.name)) return
                addPlayerToItemCoolDown(e.player.name)
                if (e.player.inventory.itemInMainHand.itemMeta == null || !e.player.inventory.itemInMainHand.itemMeta.hasDisplayName()) return
                if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK
                    || e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK
                ) {
                    when (e.player.inventory.itemInMainHand.itemMeta.displayName) {
                        GlobalConst.START_GAME_ITEM_NAME -> {
                            ImportantFunctions.skipCountdown(e.player)
                        }
                        GlobalConst.LEAVE_GAME_ITEM_NAME -> {
                            ImportantFunctions.kickPlayerLeave(e.player)
                        }
                        GlobalConst.TEAM_SELECTOR_ITEM_NAME -> {
                            Main.plugin.teamHelper.openTeamInventory(e.player)
                        }
                    }
                }
            }
            GameStates.INGAME_STATE -> {
                if (!GlobalVars.PLAYERS.containsKey(e.player)) {
                    e.isCancelled = true
                    if (itemCoolDown.contains(e.player.name)) return
                    addPlayerToItemCoolDown(e.player.name)
                    //TODO Spectator stuff!
                } else if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock?.type == Material.BEACON) e.isCancelled =
                    true
            }
            GameStates.END_STATE -> {
                e.isCancelled = true
            }
        }
    }
}