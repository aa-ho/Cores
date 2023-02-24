package cores.config

import cores.Main.Companion.configuration
import cores.Main.Companion.plugin
import cores.api.Beacons
import cores.api.Teams
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_END_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_INGAME_TOTAL_SECONDS
import cores.config.Paths.CONFIG_LOBBY_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_LOBBY_SPAWN_LOCATION
import cores.config.Paths.CONFIG_MAX_PLAYERS
import cores.config.Paths.CONFIG_MIN_PLAYERS
import cores.config.Paths.CONFIG_PREFIX
import cores.config.Paths.CONFIG_TEAM_SPAWN_BLUE
import cores.config.Paths.CONFIG_TEAM_SPAWN_RED
import org.bukkit.Location
import org.bukkit.block.Block

class ConfigGetter {
    fun getPrefix(): String = plugin.config.getString(CONFIG_PREFIX).toString()
    fun getMinPlayers(): Int = plugin.config.getInt(CONFIG_MIN_PLAYERS)
    fun getMaxPlayers(): Int = plugin.config.getInt(CONFIG_MAX_PLAYERS)
    fun getLobbyCountdownSeconds(): Int = plugin.config.getInt(CONFIG_LOBBY_COUNTDOWN_SECONDS)
    fun getIngameTotalSeconds(): Int = plugin.config.getInt(CONFIG_INGAME_TOTAL_SECONDS)
    fun getEndCountdownSeconds(): Int = plugin.config.getInt(CONFIG_END_COUNTDOWN_SECONDS)
    fun getDateTextFormat(): String = plugin.config.getString(CONFIG_DATE_TEXT_FORMAT).toString()
    fun getLobbyLocation(): Location? = configuration.gameConfigYml.getLocation(CONFIG_LOBBY_SPAWN_LOCATION)

    fun getTeamSpawn(teams: Teams): Location? {
        return if (teams == Teams.RED)
            configuration.gameConfigYml.getLocation(CONFIG_TEAM_SPAWN_RED)
        else configuration.gameConfigYml.getLocation(CONFIG_TEAM_SPAWN_BLUE)
    }
    fun getBeacon(teams: Teams, beacons: Beacons): Block? {
        return null
    }
}