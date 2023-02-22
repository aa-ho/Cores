package cores.api

import cores.api.GlobalConst.INGAME_TOTAL_SECONDS
import cores.api.GlobalConst.MAX_PLAYERS
import cores.api.GlobalVars.CURRENT_GAME_STATE
import cores.api.GlobalVars.PLAYERS
import cores.gameStates.GameState
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object Messages {
    val PREFIX = "Cores"
    val PREFIX_COLOR = "§b"
    val PREFIX_COLORED = "$PREFIX_COLOR$PREFIX"
    val CHAT_EXTENSION = " §8» §7"
    val PREFIX_CHAT = "$PREFIX_COLORED$CHAT_EXTENSION"

    val PERMISSION_BYPASS = "cores.bypass"

    val GAME_IS_STARTING = "Das Spiel startet gerade."
    val GAME_IS_ENDING = "Das Spiel endet gerade."
    val JOIN_IS_DISABLED = "Das betreten des Spiels wurde deaktiviert."
    val GAME_FULL_ONLY_VIP = "Du musst VIP sein, um einer vollen Runde beizutreten."
    val GAME_FULL = "Das Spiel ist voll."
    val KICK_FROM_VIP = "Ein VIP hat die volle Runde betreten."

    private fun sendConsole(message: String) {
        Bukkit.getConsoleSender().sendMessage("$PREFIX_CHAT$message")
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
    fun sendPlayer(p: Player, msg: String, prefix: Boolean = true) {
        p.sendMessage("${if (prefix) PREFIX_CHAT else ""}$msg")
    }
    private fun broadcastMessage(msg: String, prefix: Boolean = true) {
        Bukkit.broadcastMessage("${if (prefix) PREFIX_CHAT else ""}$msg")
    }
    fun waitingForXPlayers(i: Int) {
        broadcastMessage("Es wird noch auf §b${if (i == 1) "einen " else i}§7 Spieler gewartet.")
    }
    fun gameStartInXSecond(i: Int) {
        broadcastMessage("Das Spiel startet in §b${if (i == 1) "einer " else i}§7 Sekunde${if (i == 1) "" else "n"}.")
    }
    fun gameStartInXSecondTitle(i: Int) : String = "§b$i"
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
                if (CURRENT_GAME_STATE == GameState.END_STATE) ""
                else if (CURRENT_GAME_STATE == GameState.LOBBY_STATE) "(§b${PLAYERS.size}§7/§3$MAX_PLAYERS§7)"
                else "(rejoin)"
            }."
        )
    }
    fun halftimeBroadcast() {
        broadcastMessage("Halbzeit. Das Spiel endet in §b${INGAME_TOTAL_SECONDS/60/2} §7Minuten.")
    }
    fun gameEndsInXMinutes(minutes: Int) {
        broadcastMessage("Das Spiel endet in §b$minutes §7Minuten")
    }
    fun gameEndsInXSeconds(minutes: Int, plural: Boolean = true) {
        broadcastMessage("Das Spiel endet in §b${if (plural) minutes else "einer"} §7Sekunde${if (plural) "n" else ""}.")
    }

}