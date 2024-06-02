package cores.listener

import cores.Main
import cores.api.GlobalConst
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions
import cores.api.Messages
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent


class PlayerDeathListener : Listener {
    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        e.deathMessage = null
        when (Main.plugin.gameStateManager.getCurrentGameState()) {
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    e.drops.clear()
                    if (Main.plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) e.player.teleport(GlobalConst.TEAM_SPAWN_RED_LOCATION)
                    else e.player.teleport(GlobalConst.TEAM_SPAWN_BLUE_LOCATION)
                    if (e.player.killer != null) {
                        Messages.sendPlayerKilledByPlayer(e.player, e.player.killer!!)
                        ImportantFunctions.sendPlayerKillSound(e.player.killer!!)
                        //TODO update scoreboard?
                    } else {
                        //TODO more reasons...
                        when (e.player.lastDamageCause) {
                            //TODO...
                        }
                        Messages.sendPlayerDied(e.player)
                    }
                } else e.isCancelled = true
            }
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
        }
    }
}