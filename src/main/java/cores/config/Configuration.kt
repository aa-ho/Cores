package cores.config

import cores.Main.Companion.plugin
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.GlobalConst.END_COUNTDOWN_SECONDS
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SECONDS
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.Messages.CHECK_CONFIG_FOR_CONTENT
import cores.api.Messages.CONFIG_LOADED
import cores.api.Messages.NEW_CONFIG_CREATED
import cores.api.Messages.NO_CONFIG_FOUND_GENERATING_CONFIG
import cores.api.Messages.sendConsole
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_PREFIX
import java.io.IOException

class Configuration {

    val setter = ConfigSetter()
    val getter = ConfigGetter()

    fun saveConfig(path: String, value: Any?) {
        plugin.config.set(path, value)
        try {
            plugin.saveConfig()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadConfig() {
        MIN_PLAYERS = getter.getMinPlayers()
        MAX_PLAYERS = getter.getMaxPlayers()
        LOBBY_COUNTDOWN_SECONDS = getter.getLobbyCountdownSeconds()
        INGAME_TOTAL_SECONDS = getter.getIngameTotalSeconds()
        END_COUNTDOWN_SECONDS = getter.getEndCountdownSeconds()
        DATE_TEXT_FORMAT = getter.getDateTextFormat()
        try {
            //LOBBY_SPAWN_LOCATION = getter.getLobbyLocation()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        sendConsole(CONFIG_LOADED)
    }

    fun checkConfig() {
        sendConsole(CHECK_CONFIG_FOR_CONTENT)
        if (plugin.config.get(CONFIG_DATE_TEXT_FORMAT) == null) {
            sendConsole(NO_CONFIG_FOUND_GENERATING_CONFIG)
            defaultConfigSet()
            sendConsole(NEW_CONFIG_CREATED)
        } else {
            loadConfig()
        }
    }

    private fun defaultConfigSet() {
        setter.setMinPlayers(MIN_PLAYERS)
        setter.setMaxPlayers(MAX_PLAYERS)
        setter.setLobbyCountdownSeconds(LOBBY_COUNTDOWN_SECONDS)
        setter.setIngameTotalSeconds(INGAME_TOTAL_SECONDS)
        setter.setEndCountdownSeconds(END_COUNTDOWN_SECONDS)
        setter.setDateTextFormat(DATE_TEXT_FORMAT)
    }

}