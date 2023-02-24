package cores.config

import cores.Main.Companion.configuration
import cores.api.Beacons
import cores.api.GlobalConst.BEACON_BACK
import cores.api.GlobalConst.BEACON_FRONT
import cores.api.GlobalConst.BEACON_LEFT
import cores.api.GlobalConst.BEACON_RIGHT
import cores.api.GlobalConst.BLUE_COMMAND
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
import cores.api.GlobalConst.RED_COMMAND
import cores.api.GlobalConst.RED_CORE_BACK
import cores.api.GlobalConst.RED_CORE_FRONT
import cores.api.GlobalConst.RED_CORE_LEFT
import cores.api.GlobalConst.RED_CORE_RIGHT
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.Teams
import cores.config.Paths.CONFIG_BEACON_BACK
import cores.config.Paths.CONFIG_BEACON_FRONT
import cores.config.Paths.CONFIG_BEACON_LEFT
import cores.config.Paths.CONFIG_BEAON_RIGHT
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.CONFIG_END_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_INGAME_TOTAL_SECONDS
import cores.config.Paths.CONFIG_LOBBY_COUNTDOWN_SECONDS
import cores.config.Paths.CONFIG_LOBBY_SPAWN_LOCATION
import cores.config.Paths.CONFIG_MAX_PLAYERS
import cores.config.Paths.CONFIG_MIN_PLAYERS
import cores.config.Paths.CONFIG_PREFIX
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

    fun setTeamSpawn(teams: Teams, loc: Location) {
        if (teams == Teams.RED) {
            configuration.saveGameConfig(CONFIG_TEAM_SPAWN_RED, loc)
            TEAM_SPAWN_RED_LOCATION = loc
        } else {
            configuration.saveGameConfig(CONFIG_TEAM_SPAWN_BLUE, loc)
            TEAM_SPAWN_BLUE_LOCATION = loc
        }
    }

    fun setBeacon(team: Teams, beacon: Beacons, loc: Location) {
        val (command, core) = when (team) {
            Teams.RED -> CONFIG_TEAM_CATEGORY_RED to when (beacon) {
                Beacons.FRONT -> ::RED_CORE_FRONT
                Beacons.BACK -> ::RED_CORE_BACK
                Beacons.LEFT -> ::RED_CORE_LEFT
                Beacons.RIGHT -> ::RED_CORE_RIGHT
            }
            Teams.BLUE -> CONFIG_TEAM_CATEGORY_BLUE to when (beacon) {
                Beacons.FRONT -> ::BLUE_CORE_FRONT
                Beacons.BACK -> ::BLUE_CORE_BACK
                Beacons.LEFT -> ::BLUE_CORE_LEFT
                Beacons.RIGHT -> ::BLUE_CORE_RIGHT
            }
        }
        configuration.saveGameConfig(
            command + when (beacon) {
                Beacons.FRONT -> CONFIG_BEACON_FRONT
                Beacons.BACK -> CONFIG_BEACON_BACK
                Beacons.LEFT -> CONFIG_BEACON_LEFT
                Beacons.RIGHT -> CONFIG_BEAON_RIGHT
            }, loc
        )
        core.set(loc)
    }
}