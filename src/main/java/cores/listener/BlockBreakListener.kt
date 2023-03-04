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
import cores.api.Messages.PREFIX_COLORED
import cores.api.Messages.sendCoreDestroyed
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
                        when(e.block.location) {
                            RED_CORE_FRONT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.RED, Beacon.Front)
                                    sendCoreDestroyed(e.player, Beacon.Front)
                                }
                            }
                            RED_CORE_BACK -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.RED, Beacon.Back)
                                    sendCoreDestroyed(e.player, Beacon.Back)
                                }
                            }
                            RED_CORE_LEFT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.RED, Beacon.Left)
                                    sendCoreDestroyed(e.player, Beacon.Left)
                                }
                            }
                            RED_CORE_RIGHT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.RED) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.RED, Beacon.Right)
                                    sendCoreDestroyed(e.player, Beacon.Right)
                                }
                            }
                            BLUE_CORE_FRONT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.BLUE, Beacon.Front)
                                    sendCoreDestroyed(e.player, Beacon.Front)
                                }
                            }
                            BLUE_CORE_BACK -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.BLUE, Beacon.Back)
                                    sendCoreDestroyed(e.player, Beacon.Back)
                                }
                            }
                            BLUE_CORE_LEFT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.BLUE, Beacon.Left)
                                    sendCoreDestroyed(e.player, Beacon.Left)
                                }
                            }
                            BLUE_CORE_RIGHT -> {
                                if(plugin.teamHelper.getPlayerTeam(e.player) == Team.BLUE) {
                                    sendPlayerCannotDestroyOwnBeacon(e.player)
                                } else {
                                    plugin.cores.removeCoreFromTeam(Team.BLUE, Beacon.Right)
                                    sendCoreDestroyed(e.player, Beacon.Right)
                                }
                            }
                        }
                        e.block.type = Material.AIR
                        sendPlayer(e.player, PREFIX_COLORED)
                    } else return
                } else e.isCancelled = true
            }
        }
    }
}