package cores.listener

import cores.Main.Companion.plugin
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalVars.GAME_STARTING
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class ServerListPingListener : Listener {
    @EventHandler
    fun updateMotd(e: ServerListPingEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> {
                if (GAME_STARTING) e.motd = "§cstartet"
                else if (PLAYERS.size == MAX_PLAYERS) e.motd = "§3voll"
                else e.motd = "§3${PLAYERS.size}§7 von §3$MAX_PLAYERS"
            }
            GameStates.INGAME_STATE -> e.motd = "§3läuft"
            GameStates.END_STATE -> e.motd = "§cendet"
        }
    }
}