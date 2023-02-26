package cores.config

import cores.Main.Companion.configuration
import cores.api.Beacon
import cores.api.GlobalConst.BLUE_CORE_BACK
import cores.api.GlobalConst.BLUE_CORE_FRONT
import cores.api.GlobalConst.BLUE_CORE_LEFT
import cores.api.GlobalConst.BLUE_CORE_RIGHT
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.GlobalConst.END_COUNTDOWN_SECONDS
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SECONDS
import cores.api.GlobalConst.LOBBY_SPAWN_LOCATION
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalConst.RED_CORE_BACK
import cores.api.GlobalConst.RED_CORE_FRONT
import cores.api.GlobalConst.RED_CORE_LEFT
import cores.api.GlobalConst.RED_CORE_RIGHT
import cores.api.GlobalConst.SPECTATOR_SPAWN_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
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

    fun setLobbyLocation(loc: Location) {
        configuration.saveGameConfig(CONFIG_LOBBY_SPAWN_LOCATION, loc)
        LOBBY_SPAWN_LOCATION = loc
    }

    fun setTeamSpawn(teams: Team, loc: Location) {
        if (teams == Team.RED) {
            configuration.saveGameConfig(CONFIG_TEAM_SPAWN_RED, loc)
            TEAM_SPAWN_RED_LOCATION = loc
        } else {
            configuration.saveGameConfig(CONFIG_TEAM_SPAWN_BLUE, loc)
            TEAM_SPAWN_BLUE_LOCATION = loc
        }
    }
    fun setSpectatorSpawn(loc: Location) {
        configuration.saveGameConfig(CONFIG_SPECTATOR_SPAWN, loc)
        SPECTATOR_SPAWN_LOCATION = loc
    }

    fun setBeacon(team: Team, beacon: Beacon, loc: Location) {
        val (command, core) = when (team) {
            Team.RED -> CONFIG_TEAM_CATEGORY_RED to when (beacon) {
                Beacon.Front -> ::RED_CORE_FRONT
                Beacon.Back -> ::RED_CORE_BACK
                Beacon.Left -> ::RED_CORE_LEFT
                Beacon.Right -> ::RED_CORE_RIGHT
            }
            Team.BLUE -> CONFIG_TEAM_CATEGORY_BLUE to when (beacon) {
                Beacon.Front -> ::BLUE_CORE_FRONT
                Beacon.Back -> ::BLUE_CORE_BACK
                Beacon.Left -> ::BLUE_CORE_LEFT
                Beacon.Right -> ::BLUE_CORE_RIGHT
            }
        }
        configuration.saveGameConfig(
            command + when (beacon) {
                Beacon.Front -> CONFIG_BEACON_FRONT
                Beacon.Back -> CONFIG_BEACON_BACK
                Beacon.Left -> CONFIG_BEACON_LEFT
                Beacon.Right -> CONFIG_BEACON_RIGHT
            }, loc
        )
        core.set(loc)
    }
}