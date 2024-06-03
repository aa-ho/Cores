package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.BLUE_CORE_BACK
import cores.api.GlobalConst.BLUE_CORE_FRONT
import cores.api.GlobalConst.BLUE_CORE_LEFT
import cores.api.GlobalConst.BLUE_CORE_RIGHT
import cores.api.GlobalConst.RED_CORE_BACK
import cores.api.GlobalConst.RED_CORE_FRONT
import cores.api.GlobalConst.RED_CORE_LEFT
import cores.api.GlobalConst.RED_CORE_RIGHT
import cores.api.ImportantFunctions.updateInGameScoreboardAll
import cores.gameStates.GameStates
import org.bukkit.Location

class BeaconHelper {
     val redCores = HashMap<Beacon, Location>()
     val blueCores = HashMap<Beacon, Location>()
    fun hasTeamCores(team: Team): Boolean = if (team == Team.RED) redCores.size != 0 else blueCores.size != 0
    fun addCoreToTeam(team: Team, beacon: Beacon) {
        if (team == Team.RED) {
            if (!redCores.contains(beacon)) {
                when(beacon) {
                    Beacon.Front -> redCores[beacon] = RED_CORE_FRONT
                    Beacon.Back -> redCores[beacon] = RED_CORE_BACK
                    Beacon.Left -> redCores[beacon] = RED_CORE_LEFT
                    Beacon.Right -> redCores[beacon] = RED_CORE_RIGHT
                }
            }
        } else {
            if (!blueCores.contains(beacon)) {
                when(beacon) {
                    Beacon.Front -> blueCores[beacon] = BLUE_CORE_FRONT
                    Beacon.Back -> blueCores[beacon] = BLUE_CORE_BACK
                    Beacon.Left -> blueCores[beacon] = BLUE_CORE_LEFT
                    Beacon.Right -> blueCores[beacon] = BLUE_CORE_RIGHT
                }
            }
        }
    }

    fun removeCoreFromTeam(team: Team, beacon: Beacon) {
        if (team == Team.RED) {
            if (redCores.contains(beacon)) {
                redCores.remove(beacon)
            }
        } else if (blueCores.contains(beacon)) {
            if (hasTeamCores(Team.BLUE)) {
                blueCores.remove(beacon)
            }
        }
        updateInGameScoreboardAll()
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) plugin.gameStateManager.ingameState.gameOver()
    }
}