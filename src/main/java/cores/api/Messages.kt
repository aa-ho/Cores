package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.BEACON_BACK
import cores.api.GlobalConst.BEACON_FRONT
import cores.api.GlobalConst.BEACON_LEFT
import cores.api.GlobalConst.BEACON_RIGHT
import cores.api.GlobalConst.BLUE_COMMAND
import cores.api.GlobalConst.CORES_COMMAND
import cores.api.GlobalConst.HELP_COMMAND
import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalConst.RED_COMMAND
import cores.api.GlobalConst.SET_BEACON_COMMAND
import cores.api.GlobalConst.SET_COMMAND
import cores.api.GlobalConst.SET_TEAM_SPAWN
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object Messages {
    var PREFIX = "Cores"
    var PREFIX_COLOR = "§b"
    var PREFIX_COLORED = "$PREFIX_COLOR$PREFIX"
    var CHAT_EXTENSION = " §8» §7"
    var PREFIX_CHAT = "$PREFIX_COLORED$CHAT_EXTENSION"

    val CORES_SINGULAR_COLORED = "§bCore"

    var RED_COLORED = "§cRot"
    var BLUE_COLORED = "§9Blau"
    val RANDOM_TEAM_COLORED = "§7Zufällig"
    val SPECTATOR_COLORED = "§7Zuschauer"

    val GAME_IS_STARTING = "Das Spiel startet gerade."
    val GAME_IS_ENDING = "Das Spiel endet gerade."
    val JOIN_IS_DISABLED = "Das betreten des Spiels wurde deaktiviert."
    val GAME_FULL_ONLY_VIP = "Du musst VIP sein, um einer vollen Runde beizutreten."
    val GAME_FULL = "Das Spiel ist voll."
    val KICK_FROM_VIP = "Ein VIP hat die volle Runde betreten."
    val KICK_LEAVE_ITEM = "Du hast das Spiel verlassen."

    val CONFIG_COLORED = "§bConfig"
    val CHECK_CONFIG_FOR_CONTENT = "§eDie $CONFIG_COLORED§e wird auf Inhalte geprüft..."
    val NO_CONFIG_FOUND_GENERATING_CONFIG =
        "§eDie $CONFIG_COLORED§e ist leer oder existiert §e§nnicht§e. Config wird generiert..."
    val NEW_CONFIG_CREATED = "§e Neue $CONFIG_COLORED§e erstellt."
    val CONFIG_LOADED = "$CONFIG_COLORED§e geladen. §a✓"
    val LETS_GO = "§3Let's Go"

    private val COMMAND_HELPER_START = "Bitte nutze: §b§n/$CORES_COMMAND "

    private fun sendPlayerCommandHelper(p: Player, commands: String) {
        sendPlayer(p, "$COMMAND_HELPER_START$commands§7.")
    }

    fun sendConsole(message: String, prefix: Boolean = true) {
        Bukkit.getConsoleSender().sendMessage("${if (prefix) PREFIX_CHAT else ""}$message")
    }

    fun sendPluginDisEnabled(enabled: Boolean, timeStamp: String) {
        sendConsole(
            "Das Plugin wurde §${
                if (enabled) {
                    "aaktiviert"
                } else {
                    "cdeaktiviert"
                }
            }§7.\n\n${PREFIX_CHAT}Zeitstempel: §3$timeStamp§7.\n"
        )
    }

    fun sendStateToggled(gameState: GameStates, started: Boolean) {
        sendConsole("", false)
        sendConsole("§b$gameState §7wurde ${if (started) "§agestartet" else "§cgestoppt"}§7.")
        sendConsole("", false)
    }

    fun sendPlayer(p: Player, msg: String, prefix: Boolean = true) {
        p.sendMessage("${if (prefix) PREFIX_CHAT else ""}$msg")
    }
    fun sendSpectators(message: String) {
        Bukkit.getOnlinePlayers().forEach {
            if(!PLAYERS.containsKey(it)) sendPlayer(it, message)
        }
    }

    private fun broadcastMessage(msg: String, prefix: Boolean = true) {
        Bukkit.broadcastMessage("${if (prefix) PREFIX_CHAT else ""}$msg")
    }

    fun waitingForXPlayers(i: Int) {
        broadcastMessage("Es wird noch auf §b${if (i == 1) "einen" else i}§7 Spieler gewartet.")
    }

    fun gameStartInXSecond(i: Int) {
        broadcastMessage("Das Spiel startet in §b${if (i == 1) "einer" else i}§7 Sekunde${if (i == 1) "" else "n"}.")
    }

    fun gameStartInXSecondTitle(i: Int): String = "§b$i"

    fun serverStopInXSeconds(i: Int) {
        broadcastMessage("Der Server stoppt in §b${if (i == 1) "einer" else i}§7 Sekunde${if (i == 1) "" else "n"}.")
    }
    fun gameTitle(): String = PREFIX_COLORED
    fun broadcastPlayerJoinedGame(p: Player) {
        broadcastMessage("${plugin.rankHelper.getPlayersRankColor(p)}${p.name}§7 hat das Spiel betreten (§b${PLAYERS.size}§7/§3$MAX_PLAYERS§7).")
    }

    fun broadcastPlayerRejoinedGame(p: Player) {
        broadcastMessage("${plugin.teamHelper.getPlayerTeam(p).colorDisplayed}${p.name}§7 hat das Spiel wieder betreten.")
    }
    fun sendPlayerCannotDestroyOwnBeacon(p: Player) {
        sendPlayer(p, "Du kannst deinen eigenen $CORES_SINGULAR_COLORED§7 nicht zerstören.")
    }
    fun sendCoreDestroyed(p: Player, beacon: Beacon) {
        Bukkit.getOnlinePlayers().forEach {
            if(it.name==p.name) {
                sendPlayer(it, "Du hast den §b$beacon-$CORES_SINGULAR_COLORED§7 zerstört.")
            } else {
                sendPlayer(it, "${plugin.teamHelper.getPlayerTeam(p).colorDisplayed}${p.name}§7 hat den §b$beacon-$CORES_SINGULAR_COLORED zerstört.")
            }
        }
    }


    fun playerLeftGame(p: Player) {
        when(plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> broadcastMessage("${plugin.rankHelper.getPlayersRankColor(p)}${p.name}§7 hat das Spiel verlassen (§b${PLAYERS.size}§7/§3$MAX_PLAYERS§7).")
            GameStates.INGAME_STATE -> {
                if(PLAYERS.containsKey(p)) broadcastMessage("${plugin.teamHelper.getPlayerTeam(p).colorDisplayed}§7 hat das Spiel verlassen (rejoin).")
            }
            GameStates.END_STATE -> broadcastMessage("${plugin.rankHelper.getPlayersRankColor(p)}${p.name}§7 hat das Spiel verlassen.")
        }
    }

    fun halftimeBroadcast() {
        broadcastMessage("Halbzeit. Das Spiel endet in §b${INGAME_TOTAL_SECONDS / 60 / 2} §7Minuten.")
    }

    fun gameEndsInXMinutes(minutes: Int) {
        broadcastMessage("Das Spiel endet in §b$minutes §7Minuten")
    }

    fun gameEndsInXSeconds(minutes: Int, plural: Boolean = true) {
        broadcastMessage("Das Spiel endet in §b${if (plural) minutes else "einer"} §7Sekunde${if (plural) "n" else ""}.")
    }

    fun sendDoNotSpamCommand(p: Player) {
        sendPlayer(p, "Bitte spamme nicht.")
    }

    fun sendMissingPermission(p: Player, permission: String) {
        sendPlayer(p, permission)
    }

    fun sendPlayerOnlyLobbyStateStart(p: Player) {
        sendPlayer(p, "Du kannst den Befehl nur in der Warte-Lobby ausführen.")
    }

    fun sendPlayerLobbyCountdownNotSkippable(p: Player) {
        sendPlayer(p, "Du kannst den Countdown nicht mehr verkürzen.")
    }

    fun sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers(p: Player) {
        sendPlayer(p, "Es sind §7§nnicht§7 genügend Spieler in der Runde.")
    }

    fun sendAllLobbyCountdownSkipped(p: Player) {
        Bukkit.getOnlinePlayers().forEach {
            if (it.name == p.name) {
                sendPlayer(it, "Du hast den Lobby-Countdown verkürzt.")
            } else {
                sendPlayer(it, "§a${p.name}§7 hat den Lobby-Countdown verkürzt.")
            }
        }
    }

    fun sendPLayerLobbySet(p: Player) {
        sendPlayer(p, "Lobby Spawnpunkt gesetzt.")
    }
    fun sendPlayerSpectatorSpawnSet(p: Player) {
        sendPlayer(p, "Spectator Spawnpunkt gesetzt.")
    }

    fun sendPlayerTeamSpawnSet(p: Player, team: Team) {
        sendPlayer(p, "Spawnpunkt für Team §${if (team == Team.RED) RED_COLORED else BLUE_COLORED}§7 gesetzt.")
    }

    fun sendPlayerCoreLocSet(p: Player, team: Team, beacon: Beacon) {
        sendPlayer(p, "${if (team == Team.RED) RED_COLORED else BLUE_COLORED}§7 §b$beacon §7gesetzt.")
    }

    fun sendPlayerTeamSpawnSetHelp(p: Player) {
        sendPlayerCommandHelper(p, "$SET_COMMAND $SET_TEAM_SPAWN $RED_COMMAND/$BLUE_COMMAND")
    }

    fun sendPlayerSetCoreLocHelp(p: Player) {
        sendPlayerCommandHelper(
            p,
            "$SET_COMMAND $SET_BEACON_COMMAND $RED_COMMAND/$BLUE_COMMAND $BEACON_FRONT/$BEACON_BACK/$BEACON_LEFT/$BEACON_RIGHT"
        )
    }

    fun sendPlayerCoresCommand(p: Player) {
        sendPlayerCommandHelper(p, HELP_COMMAND)
    }

    fun sendPlayerTeamFull(p: Player, teams: Team) {
        sendPlayer(p, "Team ${if (teams == Team.RED) RED_COLORED else BLUE_COLORED}§7 ist voll.")
    }

    fun sendPlayerJoinedTeam(p: Player, teams: Team) {
        sendPlayer(p, "Du bist Team ${if (teams == Team.RED) RED_COLORED else BLUE_COLORED}§7 beigetreten.")
    }

    fun sendPlayerAlreadyInTeam(p: Player, team: Team) {
        sendPlayer(p, "Du bist bereits in Team ${if (team == Team.RED) RED_COLORED else BLUE_COLORED}§7.")
    }

    fun getPlayersScoreboard(): String {
        return "§3${if (Bukkit.getOnlinePlayers().size == MAX_PLAYERS) "voll" else "${PLAYERS.size}§7 von §3$MAX_PLAYERS"}"
    }

    fun sendPlayerRandomTeam(p: Player) {
        sendPlayer(p, "Du wirst beim Spielstart einem zufälligem Team zugeordnet.")
    }

    fun sendPlayerAlreadyRandomTeam(p: Player) {
        sendPlayer(p, "Du wirst beim Spielstart bereits einem zufälligem Team zugeordnet.")
    }

    fun teamSelectItems(teams: Team): String {
        //TODO FIX!
        /*return if(teams == Teams.RED) "§b${plugin.teamHelper.teamSize(Teams.RED)}§7/§3${MAX_PLAYERS/2}"
        else "§b${plugin.teamHelper.teamSize(Teams.BLUE)}§7/§3${MAX_PLAYERS/2}"
    */
        return if (teams == Team.RED) "§bX§7/§3${MAX_PLAYERS / 2}"
        else "§bX§7/§3${MAX_PLAYERS / 2}"
    }
    fun scoreboardLobbyCountdown(seconds: Int): String {
        return if(seconds < 1) {
            LETS_GO
        } else {
            "§3$seconds Sekunde${if(seconds>1) "n" else ""}"
        }
    }
    fun sendPlayerKilledByPlayer(player: Player, killer: Player) {
        val playerColored = "${plugin.rankHelper.getPlayersRankColor(player)}${player.name}"
        val killerColored = "${plugin.rankHelper.getPlayersRankColor(killer)}${killer.name}"
        Bukkit.getOnlinePlayers().forEach {
            when (it.name) {
                player.name -> sendPlayer(it, "Du wurdest von $killerColored§7 getötet.")
                killer.name -> sendPlayer(it, "Du hast $playerColored§7 getötet.")
                else -> sendPlayer(it, "$playerColored§7 wurde von $killerColored§7 getötet.")
            }
        }
    }
    fun sendPlayerDied(p: Player) {
        val playerColored = "${plugin.rankHelper.getPlayersRankColor(p)}${p.name}"
        Bukkit.getOnlinePlayers().forEach {
            if(it.name==p.name) sendPlayer(it, "Du bist gestorben.")
            else sendPlayer(it, "$playerColored§7 ist gestorben.")
        }
    }
    //TODO Core....
    fun sendPlayerCoreDestroyed(p: Player, ) {
        val playerColored = "${plugin.rankHelper.getPlayersRankColor(p)}${p.name}"
        Bukkit.getOnlinePlayers().forEach {
            if(it.name == p.name) {
                //TODO DO stuff...
            } else {
                //TODO DO stuff...
            }
        }
    }
}