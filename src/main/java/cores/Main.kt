package cores

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import cores.api.Cores
import cores.api.GlobalConst.CORES_COMMAND
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.GlobalConst.START_COMMAND
import cores.api.Messages.sendPluginDisEnabled
import cores.commands.CoresCommand
import cores.config.Configuration
import cores.gameStates.GameStateManager
import cores.gameStates.GameStates
import cores.listener.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Main : JavaPlugin() {
    companion object {
        lateinit var plugin: Main
        lateinit var configuration: Configuration
        lateinit var protocolManager: ProtocolManager
    }

    val gameStateManager = GameStateManager()
    val cores = Cores()

    override fun onEnable() {
        sendPluginDisEnabled(true, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
        plugin = this
        protocolManager = ProtocolLibrary.getProtocolManager()
        configuration = Configuration()
        configuration.checkConfig()
        registerEvents()
        getCommand(CORES_COMMAND)?.setExecutor(CoresCommand())
        gameStateManager.startGameState(GameStates.LOBBY_STATE)
        cores.addAllCores()
    }

    override fun onDisable() {
        sendPluginDisEnabled(false, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
        Bukkit.getOnlinePlayers().forEach {
            it.kickPlayer("Hurensohn")
        }
    }

    private fun registerEvent(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, this)
    }

    private fun registerEvents() {
        registerEvent(PlayerLoginListener())
        registerEvent(PlayerJoinListener())
        registerEvent(PlayerQuitListener())
        registerEvent(EntityDamageByEntityListener())
        registerEvent(BlockBreakListener())
        registerEvent(OtherListeners())
    }
}