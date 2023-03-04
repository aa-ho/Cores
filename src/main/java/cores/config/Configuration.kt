package cores.config

import cores.Main.Companion.plugin
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
import cores.api.Messages.CHECK_CONFIG_FOR_CONTENT
import cores.api.Messages.CONFIG_LOADED
import cores.api.Messages.NEW_CONFIG_CREATED
import cores.api.Messages.NO_CONFIG_FOUND_GENERATING_CONFIG
import cores.api.Messages.sendConsole
import cores.api.Team
import cores.config.Paths.CONFIG_DATE_TEXT_FORMAT
import cores.config.Paths.GAME_CONFIG
import cores.config.Paths.GAME_CONFIG_YML
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class Configuration {

    val setter = ConfigSetter()
    private val getter = ConfigGetter()

    fun saveConfig(path: String, value: Any?) {
        plugin.config.set(path, value)
        try {
            plugin.saveConfig()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val gameFile = File(GAME_CONFIG, GAME_CONFIG_YML)
    val gameConfigYml: FileConfiguration = YamlConfiguration.loadConfiguration(gameFile)

    fun saveGameConfig(path: String, value: Any?) {
        gameConfigYml.set(path, value)
        try {
            gameConfigYml.save(gameFile)
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
        LOBBY_SPAWN_LOCATION = getter.getLobbyLocation() ?: LOBBY_SPAWN_LOCATION
        TEAM_SPAWN_RED_LOCATION = getter.getTeamSpawn(Team.RED) ?: TEAM_SPAWN_RED_LOCATION
        TEAM_SPAWN_BLUE_LOCATION = getter.getTeamSpawn(Team.BLUE) ?: TEAM_SPAWN_BLUE_LOCATION
        SPECTATOR_SPAWN_LOCATION = getter.getSpectatorSpawn()?: SPECTATOR_SPAWN_LOCATION
        if(getter.getBeacon(Team.RED, Beacon.Front) != null) {
            RED_CORE_FRONT = getter.getBeacon(Team.RED, Beacon.Front)!!
            plugin.beaconHelper.addCoreToTeam(Team.RED, Beacon.Front)
            RED_CORE_FRONT.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.RED, Beacon.Back) != null) {
            RED_CORE_BACK = getter.getBeacon(Team.RED, Beacon.Back)!!
            plugin.beaconHelper.addCoreToTeam(Team.RED, Beacon.Back)
            RED_CORE_BACK.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.RED, Beacon.Left) != null) {
            RED_CORE_LEFT = getter.getBeacon(Team.RED, Beacon.Left)!!
            plugin.beaconHelper.addCoreToTeam(Team.RED, Beacon.Left)
            RED_CORE_LEFT.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.RED, Beacon.Right) != null) {
            RED_CORE_RIGHT = getter.getBeacon(Team.RED, Beacon.Right)!!
            plugin.beaconHelper.addCoreToTeam(Team.RED, Beacon.Right)
            RED_CORE_RIGHT.block.type = Material.BEACON
        }

        if(getter.getBeacon(Team.BLUE, Beacon.Front) != null) {
            BLUE_CORE_FRONT = getter.getBeacon(Team.BLUE, Beacon.Front)!!
            plugin.beaconHelper.addCoreToTeam(Team.BLUE, Beacon.Front)
            BLUE_CORE_FRONT.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.BLUE, Beacon.Back) != null) {
            BLUE_CORE_BACK = getter.getBeacon(Team.BLUE, Beacon.Back)!!
            plugin.beaconHelper.addCoreToTeam(Team.BLUE, Beacon.Back)
            BLUE_CORE_BACK.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.BLUE, Beacon.Left) != null) {
            BLUE_CORE_LEFT = getter.getBeacon(Team.BLUE, Beacon.Left)!!
            plugin.beaconHelper.addCoreToTeam(Team.BLUE, Beacon.Left)
            BLUE_CORE_LEFT.block.type = Material.BEACON
        }
        if(getter.getBeacon(Team.BLUE, Beacon.Right) != null) {
            BLUE_CORE_RIGHT = getter.getBeacon(Team.BLUE, Beacon.Right)!!
            plugin.beaconHelper.addCoreToTeam(Team.BLUE, Beacon.Right)
            BLUE_CORE_RIGHT.block.type = Material.BEACON
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