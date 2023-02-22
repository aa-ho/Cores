package cores.api

import cores.gameStates.GameState
import org.bukkit.block.Block
import org.bukkit.entity.Player

object GlobalVars {
    val PLAYERS = ArrayList<Player>()
    val TEAM_RED = ArrayList<Player>()
    val TEAM_BLUE = ArrayList<Player>()
    val RED_BEACONS = ArrayList<Block>()
    val BLUE_BEACONS = ArrayList<Block>()
    var GAME_STARTING = false
    var ALLOW_JOIN = true
    var CURRENT_GAME_STATE = GameState.LOBBY_STATE

}