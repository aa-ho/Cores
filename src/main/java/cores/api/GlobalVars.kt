package cores.api

import org.bukkit.block.Block
import org.bukkit.entity.Player

object GlobalVars {
    val PLAYERS = HashMap<Player, Boolean>() //Boolean = online
    val TEAM_RED = ArrayList<Player>()
    val TEAM_BLUE = ArrayList<Player>()
    val RED_BEACONS = ArrayList<Block>()
    val BLUE_BEACONS = ArrayList<Block>()
    var GAME_STARTING = false
    var ALLOW_JOIN = true

}