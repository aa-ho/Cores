package cores.config

import cores.Main.Companion.configuration
import cores.Main.Companion.plugin
import cores.api.Beacon
import cores.api.GlobalConst.BLUE_CORE_BACK
import cores.api.GlobalConst.BLUE_CORE_FRONT
import cores.api.GlobalConst.BLUE_CORE_LEFT
import cores.api.GlobalConst.BLUE_CORE_RIGHT
import cores.api.Team
import cores.config.Paths.CONFIG_BEACON_BACK
import cores.config.Paths.CONFIG_BEACON_FRONT
import cores.config.Paths.CONFIG_BEACON_LEFT
import cores.config.Paths.CONFIG_BEACON_RIGHT
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_END_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_INGAME_TOTAL_SECONDS
import cores.config.Paths.CONFIG_LOBBY_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_LOBBY_SPAWN_LOCATION
import cores.config.Paths.CONFIG_MAX_PLAYERS
import cores.config.Paths.CONFIG_MIN_PLAYERS
import cores.config.Paths.CONFIG_PREFIX
import cores.config.Paths.CONFIG_SPECTATOR_SPAWN
import cores.config.Paths.CONFIG_TEAM_CATEGORY_BLUE
import cores.config.Paths.CONFIG_TEAM_CATEGORY_RED
import cores.config.Paths.CONFIG_TEAM_SPAWN_BLUE
import cores.config.Paths.CONFIG_TEAM_SPAWN_RED
import org.bukkit.Location

class ConfigGetter {
    fun getPrefix(): String = plugin.config.getString(CONFIG_PREFIX).toString()
    fun getMinPlayers(): Int = plugin.config.getInt(CONFIG_MIN_PLAYERS)
    fun getMaxPlayers(): Int = plugin.config.getInt(CONFIG_MAX_PLAYERS)
    fun getLobbyCountdownSeconds(): Int = plugin.config.getInt(CONFIG_LOBBY_COUNTDOWN_SECONDS)
    fun getIngameTotalSeconds(): Int = plugin.config.getInt(CONFIG_INGAME_TOTAL_SECONDS)
    fun getEndCountdownSeconds(): Int = plugin.config.getInt(CONFIG_END_COUNTDOWN_SECONDS)
    fun getDateTextFormat(): String = plugin.config.getString(CONFIG_DATE_TEXT_FORMAT).toString()
    fun getLobbyLocation(): Location? = configuration.gameConfigYml.getLocation(CONFIG_LOBBY_SPAWN_LOCATION)

    fun getTeamSpawn(teams: Team): Location? {
        return if (teams == Team.RED)
            configuration.gameConfigYml.getLocation(CONFIG_TEAM_SPAWN_RED)
        else configuration.gameConfigYml.getLocation(CONFIG_TEAM_SPAWN_BLUE)
    }
    fun getSpectatorSpawn(): Location? = configuration.gameConfigYml.getLocation(CONFIG_SPECTATOR_SPAWN)

    fun getBeacon(team: Team, beacon: Beacon): Location? {
        return when (team) {
            Team.RED -> configuration.gameConfigYml.getLocation(
                when (beacon) {
                    Beacon.Front -> CONFIG_TEAM_CATEGORY_RED + CONFIG_BEACON_FRONT
                    Beacon.Back -> CONFIG_TEAM_CATEGORY_RED + CONFIG_BEACON_BACK
                    Beacon.Left -> CONFIG_TEAM_CATEGORY_RED + CONFIG_BEACON_LEFT
                    Beacon.Right -> CONFIG_TEAM_CATEGORY_RED + CONFIG_BEACON_RIGHT
                }
            )
            Team.BLUE -> configuration.gameConfigYml.getLocation(
                when (beacon) {
                    Beacon.Front -> CONFIG_TEAM_CATEGORY_BLUE + CONFIG_BEACON_FRONT
                    Beacon.Back -> CONFIG_TEAM_CATEGORY_BLUE + CONFIG_BEACON_BACK
                    Beacon.Left -> CONFIG_TEAM_CATEGORY_BLUE + CONFIG_BEACON_LEFT
                    Beacon.Right -> CONFIG_TEAM_CATEGORY_BLUE + CONFIG_BEACON_RIGHT
                }
            )
        }
    }
}