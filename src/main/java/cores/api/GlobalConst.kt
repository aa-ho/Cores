package cores.api

import org.bukkit.Sound

object GlobalConst {
    var MIN_PLAYERS = 2
    var MAX_PLAYERS = 10
    var LOBBY_COUNTDOWN_SECONDS = 10 //TODO Eigentlich 60
    var INGAME_TOTAL_SECONDS = 60*240
    var END_COUNTDOWN_SECONDS = 20
    var DATE_TEXT_FORMAT = "dd.MM.yyyy HH:mm"

    val LOBBY_COUNTDOWN_SOUND = Sound.BLOCK_NOTE_BLOCK_BANJO
    val GAME_START_SOUND = Sound.ITEM_GOAT_HORN_SOUND_3

}