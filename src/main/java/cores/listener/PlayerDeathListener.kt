package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions
import cores.api.Messages
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.scheduler.BukkitRunnable


class PlayerDeathListener : Listener {
    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        e.deathMessage = null
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    if (e.player.killer != null) {
                        Messages.sendPlayerKilledByPlayer(e.player, e.player.killer!!)
                        ImportantFunctions.sendPlayerKillSound(e.player.killer!!)
                        plugin.statsManager.addKill(e.player.killer!!.name)
                        //TODO update scoreboard?
                    } else {
                        //TODO more reasons...
                        when (e.player.lastDamageCause) {
                            //TODO...
                        }
                        Messages.sendPlayerDied(e.player)
                    }
                    plugin.statsManager.addDeath(e.player.name)
                    e.drops.clear()
                    object : BukkitRunnable() {
                        override fun run() {
                            e.player.spigot().respawn()
                            ImportantFunctions.setInGamePlayerItems(e.player)
                            e.player.teleport(if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) GlobalConst.TEAM_SPAWN_RED_LOCATION else GlobalConst.TEAM_SPAWN_BLUE_LOCATION)
                        }
                    }.runTask(plugin)
                } else {
                    e.isCancelled = true
                }
            }
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
        }
    }
}