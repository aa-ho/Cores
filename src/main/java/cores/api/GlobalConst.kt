package cores.api

import org.bukkit.Sound

object GlobalConst {
    val MIN_PLAYERS = 2
    val MAX_PLAYERS = 10

    val LOBBY_COUNTDOWN_SECONDS = 60
    val INGAME_TOTAL_SECONDS = 60*240
    val END_COUNTDOWN_SECONDS = 20

    val DATE_TEXT_FORMAT = "dd.MM.yyyy HH:mm"

    val LOBBY_COUNTDOWN_SOUND = Sound.BLOCK_NOTE_BLOCK_BANJO
    val GAME_START_SOUND = Sound.EVENT_RAID_HORN

}