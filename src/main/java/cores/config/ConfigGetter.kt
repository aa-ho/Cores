package cores.config

import cores.Main.Companion.configuration
import cores.Main.Companion.plugin
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_END_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_INGAME_TOTAL_SECONDS
import cores.config.Paths.CONFIG_LOBBY_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_MAX_PLAYERS
import cores.config.Paths.CONFIG_MIN_PLAYERS
import cores.config.Paths.CONFIG_PREFIX

class ConfigGetter {
    fun getPrefix(): String = plugin.config.getString(CONFIG_PREFIX).toString()
    fun getMinPlayers(): Int = plugin.config.getInt(CONFIG_MIN_PLAYERS)
    fun getMaxPlayers(): Int = plugin.config.getInt(CONFIG_MAX_PLAYERS)
    fun getLobbyCountdownSeconds(): Int = plugin.config.getInt(CONFIG_LOBBY_COUNTDOWN_SECONDS)
    fun getIngameTotalSeconds(): Int = plugin.config.getInt(CONFIG_INGAME_TOTAL_SECONDS)
    fun getEndCountdownSeconds(): Int = plugin.config.getInt(CONFIG_END_COUNTDOWN_SECONDS)
    fun getDateTextFormat(): String = plugin.config.getString(CONFIG_DATE_TEXT_FORMAT).toString()

    //TODO lobby location and more...
}