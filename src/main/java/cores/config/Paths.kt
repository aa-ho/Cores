package cores.config

import cores.Main.Companion.plugin

object Paths {
    val CONFIG_PREFIX = "prefix"

    val CONFIG_MIN_PLAYERS = "min_players"
    val CONFIG_MAX_PLAYERS = "max_players"
    val CONFIG_LOBBY_COUNTDOWN_SECONDS = "lobby_countdown_in_seconds"
    val CONFIG_INGAME_TOTAL_SECONDS = "total_game_time_in_seconds"
    val CONFIG_END_COUNTDOWN_SECONDS = "end_countdown_in_seconds"
    val CONFIG_DATE_TEXT_FORMAT = "date_text_format"

    val GAME_CONFIG = "plugins/${plugin.name}"
    val GAME_CONFIG_YML = "game.yml"

    val CONFIG_LOBBY_SPAWN_LOCATION = "lobby_spawn_location"
    const val CONFIG_TEAM_CATEGORY_RED = "red."
    const val CONFIG_TEAM_CATEGORY_BLUE = "blue."
    const val CONFIG_TEAM_SPAWN_RED = "${CONFIG_TEAM_CATEGORY_RED}team_spawn_red"
    const val CONFIG_TEAM_SPAWN_BLUE = "${CONFIG_TEAM_CATEGORY_BLUE}team_spawn_blue"
    const val CONFIG_BEACON_FRONT = "beacon_front"
    const val CONFIG_BEACON_BACK = "beacon_back"
    const val CONFIG_BEACON_LEFT = "beacon_left"
    const val CONFIG_BEAON_RIGHT = "beacon_right"
}