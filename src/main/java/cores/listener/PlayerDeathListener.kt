package cores.listener

import cores.Main
import cores.api.*
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener: Listener {
    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        e.deathMessage = null
        when (Main.plugin.gameStateManager.getCurrentGameState()) {
            GameStates.INGAME_STATE -> {
                if (GlobalVars.PLAYERS.containsKey(e.player)) {
                    e.drops.clear()
                    if (Main.plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) e.player.teleport(GlobalConst.TEAM_SPAWN_RED_LOCATION)
                    else e.player.teleport(GlobalConst.TEAM_SPAWN_BLUE_LOCATION)
                    if (e.player.killer != null) {
                        Messages.sendPlayerKilledByPlayer(e.player, e.player.killer!!)
                        ImportantFunctions.sendPlayerKillSound(e.player.killer!!)
                        //TODO update scoreboard?
                    } else {
                        //TODO mor reasons...
                        when (e.player.lastDamageCause) {
                            //TODO...
                        }
                        Messages.sendPlayerDied(e.player)
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
}