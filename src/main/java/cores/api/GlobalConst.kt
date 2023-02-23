package cores.api

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound

object GlobalConst {
    var MIN_PLAYERS = 2
    var MAX_PLAYERS = 10
    var LOBBY_COUNTDOWN_SECONDS = 60 //TODO Eigentlich 60
    var INGAME_TOTAL_SECONDS = 60*240
    var END_COUNTDOWN_SECONDS = 20
    var DATE_TEXT_FORMAT = "dd.MM.yyyy HH:mm"
    var LOBBY_COUNTDOWN_SKIP_SECONDS = 10

    var LOBBY_SPAWN_LOCATION = Location(Bukkit.getWorld("world"), 1.0, 1.0, 1.0)

    val LOBBY_COUNTDOWN_SOUND = Sound.BLOCK_NOTE_BLOCK_BANJO
    val GAME_START_SOUND = Sound.ITEM_GOAT_HORN_SOUND_3

    var PERMISSION_BYPASS = "cores.bypass"
    val START_COMMAND = "start"
    val SET_COMMAND = "set"
    val SET_LOBBY_COMMAND = "lobby"
}