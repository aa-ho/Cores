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
import org.bukkit.scoreboard.Team

object Messages {
    var PREFIX = "Cores"
    var PREFIX_COLOR = "§b"
    var PREFIX_COLORED = "$PREFIX_COLOR$PREFIX"
    var CHAT_EXTENSION = " §8» §7"
    var PREFIX_CHAT = "$PREFIX_COLORED$CHAT_EXTENSION"

    var RED_COLORED = "§cRot"
    var BLUE_COLORED = "§9Blau"

    val GAME_IS_STARTING = "Das Spiel startet gerade."
    val GAME_IS_ENDING = "Das Spiel endet gerade."
    val JOIN_IS_DISABLED = "Das betreten des Spiels wurde deaktiviert."
    val GAME_FULL_ONLY_VIP = "Du musst VIP sein, um einer vollen Runde beizutreten."
    val GAME_FULL = "Das Spiel ist voll."
    val KICK_FROM_VIP = "Ein VIP hat die volle Runde betreten."

    val CONFIG_COLORED = "§bConfig"
    val CHECK_CONFIG_FOR_CONTENT = "§eDie $CONFIG_COLORED§e wird auf Inhalte geprüft..."
    val NO_CONFIG_FOUND_GENERATING_CONFIG =
        "§eDie $CONFIG_COLORED§e ist leer oder existiert §e§nnicht§e. Config wird generiert..."
    val NEW_CONFIG_CREATED = "§e Neue $CONFIG_COLORED§e erstellt."
    val CONFIG_LOADED = "$CONFIG_COLORED§e geladen. §a✓"

    val COMMAND_HELPER_START = "Bitte nutze: §b§n/$CORES_COMMAND "

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

    fun gameStartTitle(): String = "§bGo"
    fun gameTitle(): String = PREFIX_COLORED
    fun playerJoinedGame(name: String) {
        broadcastMessage("§a$name§7 hat das Spiel betreten (§b${PLAYERS.size}§7/§3$MAX_PLAYERS§7).")
    }

    fun playerRejoinedGame(name: String) {
        broadcastMessage("§a$name§7 hat das Spiel wieder betreten.")
    }

    fun playerLeftGame(name: String) {
        broadcastMessage(
            "§c$name§7 hat das Spiel verlassen ${
                if (plugin.gameStateManager.getCurrentGameState() == GameStates.END_STATE) ""
                else if (plugin.gameStateManager.getCurrentGameState() == GameStates.LOBBY_STATE) "(§b${PLAYERS.size}§7/§3$MAX_PLAYERS§7)"
                else "(rejoin)"
            }."
        )
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
            if(it.name == p.name) {
                sendPlayer(it, "Du hast den Lobby-Countdown verkürzt.")
            } else {
                sendPlayer(it, "${p.name}§7 hat den Lobby-Countdown verkürzt.")
            }
        }
    }
    fun sendPLayerLobbySet(p: Player) {
        sendPlayer(p, "Lobby Spawnpunkt gesetzt.")
    }
    fun sendPlayerTeamSpawnSet(p: Player, team: Teams) {
        sendPlayer(p, "Spawnpunkt für Team §${if(team == Teams.RED) RED_COLORED else BLUE_COLORED}§7 gesetzt.")
    }
    fun sendPlayerCoreLocSet(p: Player, team: Teams, beacon: Beacons) {
        sendPlayer(p, "${if(team == Teams.RED) RED_COLORED else BLUE_COLORED}§7 §b$beacon §7gesetzt.")
    }
    fun sendPlayerTeamSpawnSetHelp(p: Player) {
        sendPlayerCommandHelper(p, "$SET_COMMAND $SET_TEAM_SPAWN $RED_COMMAND/$BLUE_COMMAND")
    }
    fun sendPlayerSetCoreLocHelp(p: Player) {
        sendPlayerCommandHelper(p, "$SET_COMMAND $SET_BEACON_COMMAND $RED_COMMAND/$BLUE_COMMAND $BEACON_FRONT/$BEACON_BACK/$BEACON_LEFT/$BEACON_RIGHT")
    }
    fun sendPlayerCoresCommand(p: Player) {
        sendPlayerCommandHelper(p, HELP_COMMAND)
    }
}