package cores.api

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

object GlobalVars {
    val PLAYERS = HashMap<Player, Boolean>() //Boolean = online
    val RED_BEACONS = ArrayList<Location>()
    val BLUE_BEACONS = ArrayList<Location>()
    var GAME_STARTING = false
    var ALLOW_JOIN = true

}