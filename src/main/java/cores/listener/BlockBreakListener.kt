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
import cores.api.Messages.sendPlayerCannotDestroyOwnBeacon
import cores.api.Team
import cores.gameStates.GameStates
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {

    private fun handleCoreDestruction(player: Player, team: Team, beacon: Beacon, block: Block) {
        if (plugin.teamHelper.getPlayerTeam(player) == team) {
            sendPlayerCannotDestroyOwnBeacon(player)
            sendPlayerFailedSound(player)
        } else {
            onCoreDestroyed(team, beacon, player)
            plugin.beaconHelper.removeCoreFromTeam(team, beacon)
            block.type = Material.AIR
            plugin.statsManager.addCoresDestroyed(player.name)
        }
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        when (plugin.gameStateManager.getCurrentGameState()) {
            GameStates.LOBBY_STATE -> e.isCancelled = true
            GameStates.END_STATE -> e.isCancelled = true
            GameStates.INGAME_STATE -> {
                if (PLAYERS.containsKey(e.player)) {
                    if (e.block.type == Material.BEACON) {
                        e.isCancelled = true
                        when (e.block.location) {
                            RED_CORE_FRONT -> handleCoreDestruction(e.player, Team.RED, Beacon.Front, e.block)
                            RED_CORE_BACK -> handleCoreDestruction(e.player, Team.RED, Beacon.Back, e.block)
                            RED_CORE_LEFT -> handleCoreDestruction(e.player, Team.RED, Beacon.Left, e.block)
                            RED_CORE_RIGHT -> handleCoreDestruction(e.player, Team.RED, Beacon.Right, e.block)
                            BLUE_CORE_FRONT -> handleCoreDestruction(e.player, Team.BLUE, Beacon.Front, e.block)
                            BLUE_CORE_BACK -> handleCoreDestruction(e.player, Team.BLUE, Beacon.Back, e.block)
                            BLUE_CORE_LEFT -> handleCoreDestruction(e.player, Team.BLUE, Beacon.Left, e.block)
                            BLUE_CORE_RIGHT -> handleCoreDestruction(e.player, Team.BLUE, Beacon.Right, e.block)
                        }
                    } else return
                } else e.isCancelled = true
            }
        }
    }
}