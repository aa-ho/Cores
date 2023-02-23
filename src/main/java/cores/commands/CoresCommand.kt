package cores.commands

import cores.Main.Companion.plugin
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SKIP_SECONDS
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalConst.SET_COMMAND
import cores.api.GlobalConst.SET_LOBBY_COMMAND
import cores.api.GlobalConst.START_COMMAND
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.sendAllLobbyCountdownSkipped
import cores.api.Messages.sendDoNotSpamCommand
import cores.api.Messages.sendMissingPermission
import cores.api.Messages.sendPLayerLobbySet
import cores.api.Messages.sendPlayerLobbyCountdownNotSkippable
import cores.api.Messages.sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers
import cores.api.Messages.sendPlayerOnlyLobbyStateStart
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private val commandCoolDown = arrayListOf<String>()

private fun addPlayerToCommandCoolDown(name: String) {
    commandCoolDown.add(name)
    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
        commandCoolDown.remove(name)
    }, 20)
}

class CoresCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            val p: Player = sender
            if (commandCoolDown.contains(p.name)) {
                sendDoNotSpamCommand(p)
            } else {
                addPlayerToCommandCoolDown(p.name)
                if (args != null) {
                    when (args.size) {
                        0 -> {
                        }
                        1 -> {
                            when (args[0]) {
                                START_COMMAND -> {
                                    if (p.hasPermission(PERMISSION_BYPASS)) {
                                        if (plugin.gameStateManager.getCurrentGameState() == GameStates.LOBBY_STATE) {
                                            if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling && plugin.gameStateManager.lobbyState.lobbyCountdown.seconds <= LOBBY_COUNTDOWN_SKIP_SECONDS) {
                                                sendPlayerLobbyCountdownNotSkippable(p)
                                            } else if (PLAYERS.size < MIN_PLAYERS) {
                                                sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers(p)
                                            } else {
                                                plugin.gameStateManager.lobbyState.lobbyCountdown.seconds = LOBBY_COUNTDOWN_SKIP_SECONDS
                                                sendAllLobbyCountdownSkipped(p)
                                            }
                                        } else {
                                            sendPlayerOnlyLobbyStateStart(p)
                                        }
                                    } else {
                                        sendMissingPermission(p, PERMISSION_BYPASS)
                                    }
                                }
                            }
                        }
                        else -> {
                            when(args[0]) {
                                SET_COMMAND -> {
                                    if(p.hasPermission(PERMISSION_BYPASS)) {
                                        when(args[1]) {
                                            SET_LOBBY_COMMAND -> {
                                                //TODO setlobby!
                                                sendPLayerLobbySet(p)
                                            }
                                        }
                                    } else {
                                        sendMissingPermission(p, PERMISSION_BYPASS)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}