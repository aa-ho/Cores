package cores.listener

import cores.Main.Companion.gameStateManager
import cores.Main.Companion.plugin
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalVars.ALLOW_JOIN
import cores.api.GlobalVars.GAME_STARTING
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.GAME_FULL
import cores.api.Messages.GAME_FULL_ONLY_VIP
import cores.api.Messages.GAME_IS_ENDING
import cores.api.Messages.GAME_IS_STARTING
import cores.api.Messages.JOIN_IS_DISABLED
import cores.api.Messages.KICK_FROM_VIP
import cores.gameStates.GameStates
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLoginListener : Listener {
    @EventHandler
    fun connect(e: PlayerLoginEvent) {
        if (ALLOW_JOIN) {
            when (gameStateManager.getCurrentGameState()) {
                GameStates.LOBBY_STATE -> {
                    if (GAME_STARTING) {
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_IS_STARTING)
                    } else if (PLAYERS.size == MAX_PLAYERS) {
                        if (e.player.hasPermission(PERMISSION_BYPASS)) {
                            PLAYERS.forEach {
                                if (!it.key.hasPermission(PERMISSION_BYPASS)) {
                                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, KICK_FROM_VIP)
                                    return
                                }
                            }
                            e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_FULL)
                        } else {
                            e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_FULL_ONLY_VIP)
                        }
                    } else {
                        //TODO ???
                    }
                }
                GameStates.INGAME_STATE -> {

                }
                GameStates.END_STATE -> {
                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_IS_ENDING)
                }
            }
        } else {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, JOIN_IS_DISABLED)
        }
    }
}