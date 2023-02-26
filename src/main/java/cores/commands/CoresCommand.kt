package cores.commands

import cores.Main.Companion.configuration
import cores.Main.Companion.plugin
import cores.api.Beacon
import cores.api.GlobalConst.BEACON_BACK
import cores.api.GlobalConst.BEACON_FRONT
import cores.api.GlobalConst.BEACON_LEFT
import cores.api.GlobalConst.BEACON_RIGHT
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalConst.SET_COMMAND
import cores.api.GlobalConst.SET_LOBBY_COMMAND
import cores.api.GlobalConst.SET_TEAM_SPAWN
import cores.api.GlobalConst.START_COMMAND
import cores.api.GlobalConst.BLUE_COMMAND
import cores.api.GlobalConst.RED_COMMAND
import cores.api.GlobalConst.SET_BEACON_COMMAND
import cores.api.GlobalConst.SET_SPECTATOR_SPAWN
import cores.api.ImportantFunctions.playSoundLevelSuccess
import cores.api.ImportantFunctions.skipCountdown
import cores.api.Messages.sendDoNotSpamCommand
import cores.api.Messages.sendMissingPermission
import cores.api.Messages.sendPLayerLobbySet
import cores.api.Messages.sendPlayerCoreLocSet
import cores.api.Messages.sendPlayerCoresCommand
import cores.api.Messages.sendPlayerSetCoreLocHelp
import cores.api.Messages.sendPlayerSpectatorSpawnSet
import cores.api.Messages.sendPlayerTeamSpawnSet
import cores.api.Messages.sendPlayerTeamSpawnSetHelp
import cores.api.Team
import org.bukkit.Bukkit
import org.bukkit.Location
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
        }, 10)
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
                                        skipCountdown(p)
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
                                                playSoundLevelSuccess(p)
                                            }
                                            SET_TEAM_SPAWN -> {
                                                if (args.size == 3) {
                                                    if (args[2] == RED_COMMAND) {
                                                        sendPlayerTeamSpawnSet(p, Team.RED)
                                                        configuration.setter.setTeamSpawn(Team.RED, p.location)
                                                        playSoundLevelSuccess(p)
                                                    } else if (args[2] == BLUE_COMMAND) {
                                                        sendPlayerTeamSpawnSet(p, Team.BLUE)
                                                        configuration.setter.setTeamSpawn(Team.BLUE, p.location)
                                                        playSoundLevelSuccess(p)
                                                    } else {
                                                        sendPlayerTeamSpawnSetHelp(p)
                                                    }
                                                } else {
                                                    sendPlayerTeamSpawnSetHelp(p)
                                                }
                                            }
                                            SET_SPECTATOR_SPAWN -> {
                                                configuration.setter.setSpectatorSpawn(p.location)
                                                sendPlayerSpectatorSpawnSet(p)
                                                playSoundLevelSuccess(p)
                                            }
                                            SET_BEACON_COMMAND -> {
                                                if (args.size == 4) {
                                                    if (args[2] == RED_COMMAND) {
                                                        setBeacon(args[3], Team.RED, p)
                                                        playSoundLevelSuccess(p)
                                                    } else if (args[2] == BLUE_COMMAND) {
                                                        setBeacon(args[3], Team.BLUE, p)
                                                        playSoundLevelSuccess(p)
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

    private fun setBeacon(beacon: String, team: Team, p: Player) {
        val blockPosition: Location =
            p.location.block.getRelative(BlockFace.SELF).location
        when (beacon) {
            BEACON_FRONT -> {
                configuration.setter.setBeacon(
                    team,
                    Beacon.Front,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacon.Front)
            }
            BEACON_BACK -> {
                configuration.setter.setBeacon(
                    team, Beacon.Back,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacon.Back)
            }
            BEACON_LEFT -> {
                configuration.setter.setBeacon(
                    team, Beacon.Left,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacon.Left)
            }
            BEACON_RIGHT -> {
                configuration.setter.setBeacon(
                    team, Beacon.Right,
                    blockPosition
                )
                sendPlayerCoreLocSet(p, team, Beacon.Right)
            }
            else -> {
                sendPlayerSetCoreLocHelp(p)
            }
        }
    }
}