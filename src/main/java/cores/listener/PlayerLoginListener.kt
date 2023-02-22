package cores.listener

import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalVars.ALLOW_JOIN
import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.GAME_STARTING
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.GAME_FULL
import cores.api.Messages.GAME_FULL_ONLY_VIP
import cores.api.Messages.GAME_IS_ENDING
import cores.api.Messages.GAME_IS_STARTING
import cores.api.Messages.JOIN_IS_DISABLED
import cores.api.Messages.KICK_FROM_VIP
import cores.api.Messages.PERMISSION_BYPASS
import cores.api.Messages.playerJoinedGame
import cores.gameStates.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLoginListener : Listener {
    @EventHandler
    fun connect(e: PlayerLoginEvent) {
        if(ALLOW_JOIN) {
            if (GAME_STARTING) {
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_IS_STARTING)
            } else {
                when(CURRENT_GAME_STATE) {
                    GameState.LOBBY_STATE -> {
                        if (PLAYERS.size == MAX_PLAYERS) {
                            if (e.player.hasPermission(PERMISSION_BYPASS)) {
                                PLAYERS.forEach {
                                    if (!it.hasPermission(PERMISSION_BYPASS)) {
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
                    GameState.INGAME_STATE -> {

                    }
                    GameState.END_STATE -> {
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, GAME_IS_ENDING)
                    }
                }
            }
        } else {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, JOIN_IS_DISABLED)
        }
    }
}