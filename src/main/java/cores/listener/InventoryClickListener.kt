package cores.listener

import cores.Main
import cores.api.GlobalVars
import cores.api.Messages
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickListener : Listener {
    private val itemClickCoolDown = arrayListOf<String>()
    private fun addPlayerToItemClickCoolDown(name: String) {
        itemClickCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, {
            itemClickCoolDown.remove(name)
        }, 10)
    }

    @EventHandler
    fun inventoryClick(e: InventoryClickEvent) {
        if (e.whoClicked !is Player) return
        if (e.currentItem == null) return
        if (e.currentItem!!.itemMeta == null) return
        val p = e.whoClicked as Player
        if (Main.plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) {
            if (GlobalVars.PLAYERS.containsKey(p)) return
            e.isCancelled = true
        } else {
            e.isCancelled = true
            if (itemClickCoolDown.contains(p.name)) return
            addPlayerToItemClickCoolDown(p.name)
            when (e.currentItem!!.itemMeta.displayName) {
                Messages.RED_COLORED -> Main.plugin.teamHelper.joinTeam(p, Team.RED)
                Messages.BLUE_COLORED -> Main.plugin.teamHelper.joinTeam(p, Team.BLUE)
                Messages.RANDOM_TEAM_COLORED -> Main.plugin.teamHelper.willPutPlayerInRandomTeam(p)
            }
        }
    }
}