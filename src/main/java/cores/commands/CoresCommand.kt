package cores.commands

import cores.Main.Companion.configuration
import cores.Main.Companion.plugin
import cores.api.Beacons
import cores.api.GlobalConst.BEACON_BACK
import cores.api.GlobalConst.BEACON_FRONT
import cores.api.GlobalConst.BEACON_LEFT
import cores.api.GlobalConst.BEACON_RIGHT
import cores.api.GlobalConst.LOBBY_COUNTDOWN_SKIP_SECONDS
import cores.api.GlobalConst.MIN_PLAYERS
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalConst.SET_COMMAND
import cores.api.GlobalConst.SET_LOBBY_COMMAND
import cores.api.GlobalConst.SET_TEAM_SPAWN
import cores.api.GlobalConst.START_COMMAND
import cores.api.GlobalConst.BLUE_COMMAND
import cores.api.GlobalConst.RED_COMMAND
import cores.api.GlobalConst.SET_BEACON_COMMAND
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.sendAllLobbyCountdownSkipped
import cores.api.Messages.sendDoNotSpamCommand
import cores.api.Messages.sendMissingPermission
import cores.api.Messages.sendPLayerLobbySet
import cores.api.Messages.sendPlayer
import cores.api.Messages.sendPlayerCoreLocSet
import cores.api.Messages.sendPlayerCoresCommand
import cores.api.Messages.sendPlayerLobbyCountdownNotSkippable
import cores.api.Messages.sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers
import cores.api.Messages.sendPlayerOnlyLobbyStateStart
import cores.api.Messages.sendPlayerSetCoreLocHelp
import cores.api.Messages.sendPlayerTeamSpawnSet
import cores.api.Messages.sendPlayerTeamSpawnSetHelp
import cores.api.Teams
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CoresCommand : CommandExecutor {
    private val commandCoolDown = arrayListOf<String>()
    private fun addPlayerToCommandCoolDown(name: String) {
        commandCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            commandCoolDown.remove(name)
        }, 20)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            val p: Player = sender
            if (p.hasPermission(PERMISSION_BYPASS)) {
                if (commandCoolDown.contains(p.name)) {
                    sendDoNotSpamCommand(p)
                } else {
                    addPlayerToCommandCoolDown(p.name)
                    if (args != null) {
                        when (args.size) {
                            0 -> {
                                sendPlayerCoresCommand(p)
                            }
                            1 -> {
                                when (args[0]) {
                                    START_COMMAND -> {
                                        if (plugin.gameStateManager.getCurrentGameState() == GameStates.LOBBY_STATE) {
                                            if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling && plugin.gameStateManager.lobbyState.lobbyCountdown.seconds <= LOBBY_COUNTDOWN_SKIP_SECONDS) {
                                                sendPlayerLobbyCountdownNotSkippable(p)
                                            } else if (PLAYERS.size < MIN_PLAYERS) {
                                                sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers(p)
                                            } else {
                                                plugin.gameStateManager.lobbyState.lobbyCountdown.seconds =
                                                    LOBBY_COUNTDOWN_SKIP_SECONDS
                                                sendAllLobbyCountdownSkipped(p)
                                            }
                                        } else {
                                            sendPlayerOnlyLobbyStateStart(p)
                                        }

                                    }
                                }
                            }
                            else -> {
                                when (args[0]) {
                                    SET_COMMAND -> {
                                        when (args[1]) {
                                            SET_LOBBY_COMMAND -> {
                                                configuration.setter.setLobbyLocation(p.location)
                                                sendPLayerLobbySet(p)
                                            }
                                            SET_TEAM_SPAWN -> {
                                                if (args.size == 3) {
                                                    if (args[2] == RED_COMMAND) {
                                                        sendPlayerTeamSpawnSet(p, Teams.RED)
                                                        configuration.setter.setTeamSpawn(Teams.RED, p.location)
                                                    } else if (args[2] == BLUE_COMMAND) {
                                                        sendPlayerTeamSpawnSet(p, Teams.BLUE)
                                                        configuration.setter.setTeamSpawn(Teams.BLUE, p.location)
                                                    } else {
                                                        sendPlayerTeamSpawnSetHelp(p)
                                                    }
                                                } else {
                                                    sendPlayerTeamSpawnSetHelp(p)
                                                }
                                            }
                                            SET_BEACON_COMMAND -> {
                                                if (args.size == 4) {
                                                    if (args[2] == RED_COMMAND) {
                                                        setBeacon(args[3], Teams.RED, p)
                                                    } else if (args[2] == BLUE_COMMAND) {
                                                        setBeacon(args[3], Teams.BLUE, p)

                                                    } else {
                                                        sendPlayerSetCoreLocHelp(p)
                                                    }
                                                } else {
                                                    sendPlayerSetCoreLocHelp(p)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                sendMissingPermission(p, PERMISSION_BYPASS)
            }
        }
        return false
    }

    private fun setBeacon(beacon: String, team: Teams, p: Player) {
        val blockPosition: Location =
            p.location.block.getRelative(BlockFace.SELF).location
        when (beacon) {
            BEACON_FRONT -> {
                configuration.setter.setBeacon(
                    team,
                    Beacons.FRONT,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacons.FRONT)
            }
            BEACON_BACK -> {
                configuration.setter.setBeacon(
                    team, Beacons.BACK,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacons.BACK)
            }
            BEACON_LEFT -> {
                configuration.setter.setBeacon(
                    team, Beacons.LEFT,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacons.LEFT)
            }
            BEACON_RIGHT -> {
                configuration.setter.setBeacon(
                    team, Beacons.RIGHT,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacons.RIGHT)
            }
            else -> {
                sendPlayerSetCoreLocHelp(p)
            }
        }
    }
}