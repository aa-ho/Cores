package cores

import cores.api.*
import cores.api.GlobalConst.CORES_COMMAND
import cores.api.GlobalConst.DATE_TEXT_FORMAT
import cores.api.ImportantFunctions.kickAll
import cores.api.Messages.KICK_RESTART
import cores.api.Messages.sendPluginDisEnabled
import cores.commands.CoresCommand
import cores.config.Configuration
import cores.countdown.ActionBarIdle
import cores.gameStates.GameStateManager
import cores.gameStates.GameStates
import cores.listener.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Main : JavaPlugin() {
    init {
        plugin = this
    }

    companion object {
        lateinit var plugin: Main
        lateinit var configuration: Configuration
        //lateinit var protocolManager: ProtocolManager
    }

    val gameStateManager = GameStateManager()
    val beaconHelper = BeaconHelper()
    val teamHelper = TeamHelper()
    val scoreboard = Scoreboard()
    val actionBarIdle = ActionBarIdle() //TODO Don't like that!
    val rankHelper = RankHelper()
    val statsManager = StatsManager()
    val mapHelper = MapSaveAndUnloader()


    override fun onEnable() {
        sendPluginDisEnabled(true, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
        configuration = Configuration()
        configuration.checkConfig()
        registerEvents()
        getCommand(CORES_COMMAND)?.setExecutor(CoresCommand())
        teamHelper.buildTeamInventory()
        actionBarIdle.start()
        gameStateManager.startGameState(GameStates.LOBBY_STATE)
        mapHelper.save()
    }

    override fun onDisable() {
        sendPluginDisEnabled(false, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)))
        kickAll(KICK_RESTART)
        mapHelper.reset()
    }

    private fun registerEvents() {
        registerEvent(BlockBreakListener())
        registerEvent(EntityDamageByEntityListener())
        registerEvent(InventoryClickListener())
        registerEvent(OtherListeners())
        registerEvent(PlayerChatEvent())
        registerEvent(PlayerDeathListener())
        registerEvent(PlayerInteractListener())
        registerEvent(PlayerJoinListener())
        registerEvent(PlayerLoginListener())
        registerEvent(PlayerQuitListener())
        registerEvent(ServerListPingListener())
    }

    private fun registerEvent(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, this)
    }
}