package cores.api

import fr.skytasul.guardianbeam.Laser.GuardianLaser
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Beacon
import org.bukkit.entity.Player

object GlobalConst {
    var MIN_PLAYERS = 2
    var MAX_PLAYERS = 10
    var LOBBY_COUNTDOWN_SECONDS = 60 //TODO Eigentlich 60
    var INGAME_TOTAL_SECONDS = 60 * 240
    var END_COUNTDOWN_SECONDS = 20
    var DATE_TEXT_FORMAT = "dd.MM.yyyy HH:mm"
    var LOBBY_COUNTDOWN_SKIP_SECONDS = 10

    var LOBBY_SPAWN_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var TEAM_SPAWN_RED_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var TEAM_SPAWN_BLUE_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    var RED_CORE_FRONT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_BACK = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_LEFT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_RIGHT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    var BLUE_CORE_FRONT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_BACK = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_LEFT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_RIGHT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    val LOBBY_COUNTDOWN_SOUND = Sound.BLOCK_NOTE_BLOCK_BANJO
    val GAME_START_SOUND = Sound.ITEM_GOAT_HORN_SOUND_3
    val START_GAME_ITEM_TITLE = "§5Countdown verkürzen"
    val STAR_GAME_ITEM = ItemBuilder(Material.FEATHER).setDisplayName(START_GAME_ITEM_TITLE).build()

    val LEAVE_GAME_ITEM_TITLE = "§cSpiel verlassen"
    val LEAVE_GAME_ITEM = ItemBuilder(Material.STRUCTURE_VOID).setDisplayName(LEAVE_GAME_ITEM_TITLE).build()

    var PERMISSION_BYPASS = "cores.bypass"
    val CORES_COMMAND = "cores"
    val START_COMMAND = "start"
    val SET_COMMAND = "set"
    val SET_LOBBY_COMMAND = "lobby"
    val SET_TEAM_SPAWN = "spawn"
    val RED_COMMAND = "red"
    val BLUE_COMMAND = "blue"
    val HELP_COMMAND = "help"
    val SET_BEACON_COMMAND = "beacon"
    val BEACON_FRONT = "front"
    val BEACON_BACK = "back"
    val BEACON_LEFT = "left"
    val BEACON_RIGHT = "right"
}