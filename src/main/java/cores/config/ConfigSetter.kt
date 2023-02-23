package cores.config

import cores.Main.Companion.configuration
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.GlobalConst.END_COUNTDOWN_SECONDS
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SECONDS
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.MIN_PLAYERS
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_END_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_INGAME_TOTAL_SECONDS
import cores.config.Paths.CONFIG_LOBBY_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_MAX_PLAYERS
import cores.config.Paths.CONFIG_MIN_PLAYERS
import cores.config.Paths.CONFIG_PREFIX

class ConfigSetter {
    fun setPrefix(prefix: String) {
        configuration.saveConfig(CONFIG_PREFIX, prefix)
    }
    fun setMinPlayers(i: Int) {
        configuration.saveConfig(CONFIG_MIN_PLAYERS, i)
        MIN_PLAYERS = i
    }
    fun setMaxPlayers(i: Int) {
        configuration.saveConfig(CONFIG_MAX_PLAYERS, i)
        MAX_PLAYERS = i
    }
    fun setLobbyCountdownSeconds(i: Int) {
        configuration.saveConfig(CONFIG_LOBBY_COUNTDOWN_SECONDS, i)
        LOBBY_COUNTDOWN_SECONDS = i
    }
    fun setIngameTotalSeconds(i: Int) {
        configuration.saveConfig(CONFIG_INGAME_TOTAL_SECONDS, i)
        INGAME_TOTAL_SECONDS = i
    }
    fun setEndCountdownSeconds(i: Int) {
        configuration.saveConfig(CONFIG_END_COUNTDOWN_SECONDS, i)
        END_COUNTDOWN_SECONDS = i
    }
    fun setDateTextFormat(format: String) {
        configuration.saveConfig(CONFIG_DATE_TEXT_FORMAT, format)
        DATE_TEXT_FORMAT = format
    }
}