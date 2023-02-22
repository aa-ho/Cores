package cores

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.Messages.sendPluginDisEnabled
import cores.listener.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Main : JavaPlugin() {
    companion object {
        lateinit var plugin: Main
    }
    var protocolManager: ProtocolManager? = null

    override fun onEnable() {
        plugin = this
        protocolManager = ProtocolLibrary.getProtocolManager()
        registerEvent(PlayerLoginListener())
        registerEvent(PlayerJoinListener())
        registerEvent(PlayerQuitListener())
        registerEvent(EntityDamageByEntityListener())
        registerEvent(BlockBreakListener())
        registerEvent(OtherListeners())
        sendPluginDisEnabled(true, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
    }

    override fun onDisable() {
        sendPluginDisEnabled(false, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
    }

    fun registerEvent(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, this)
    }
}