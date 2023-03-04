package cores.listener

import cores.Main.Companion.plugin
import cores.api.Beacon
import cores.api.GlobalConst.BLUE_CORE_BACK
import cores.api.GlobalConst.BLUE_CORE_FRONT
import cores.api.GlobalConst.BLUE_CORE_LEFT
import cores.api.GlobalConst.BLUE_CORE_RIGHT
import cores.api.GlobalConst.RED_CORE_BACK
import cores.api.GlobalConst.RED_CORE_FRONT
import cores.api.GlobalConst.RED_CORE_LEFT
import cores.api.GlobalConst.RED_CORE_RIGHT
import cores.api.GlobalVars.PLAYERS
import cores.api.ImportantFunctions.onCoreDestroyed
import cores.api.ImportantFunctions.sendPlayerFailedSound
import cores.api.Messages.PREFIX_COLORED
import cores.api.Messages.sendPlayer
import cores.api.Messages.sendPlayerCannotDestroyOwnBeacon
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    if (e.block.type == Material.BEACON) {
                        //TODO beacon entfernt...
                        e.isCancelled = true
                        when (e.block.location) {
                            RED_CORE_FRONT, RED_CORE_BACK, RED_CORE_LEFT, RED_CORE_RIGHT, BLUE_CORE_FRONT, BLUE_CORE_BACK, BLUE_CORE_LEFT, BLUE_CORE_RIGHT -> {
                                when (e.block.location) {
                                    RED_CORE_FRONT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.RED, Beacon.Front)
                                            onCoreDestroyed(Team.RED, Beacon.Front, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    RED_CORE_BACK -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.RED, Beacon.Back)
                                            onCoreDestroyed(Team.RED, Beacon.Back, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    RED_CORE_LEFT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.RED, Beacon.Left)
                                            onCoreDestroyed(Team.RED, Beacon.Left, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    RED_CORE_RIGHT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.RED, Beacon.Right)
                                            onCoreDestroyed(Team.RED, Beacon.Right, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    BLUE_CORE_FRONT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.BLUE, Beacon.Front)
                                            onCoreDestroyed(Team.BLUE, Beacon.Front, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    BLUE_CORE_BACK -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.BLUE, Beacon.Back)
                                            onCoreDestroyed(Team.BLUE, Beacon.Back, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    BLUE_CORE_LEFT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.BLUE, Beacon.Left)
                                            onCoreDestroyed(Team.BLUE, Beacon.Left, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                    BLUE_CORE_RIGHT -> {
                                        if (plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                            sendPlayerCannotDestroyOwnBeacon(e.player)
                                            sendPlayerFailedSound(e.player)
                                        } else {
                                            plugin.beaconHelper.removeCoreFromTeam(Team.BLUE, Beacon.Right)
                                            onCoreDestroyed(Team.BLUE, Beacon.Right, e.player)
                                            e.block.type = Material.AIR
                                        }
                                    }
                                }
                            }
                        }
                        sendPlayer(e.player, PREFIX_COLORED)
                    } else return
                } else e.isCancelled = true
            }
        }
    }
}