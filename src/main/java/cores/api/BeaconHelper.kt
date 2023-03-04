package cores.api

import cores.Main.Companion.plugin
import cores.api.ImportantFunctions.updateInGameScoreboardAll
import cores.gameStates.GameStates

class BeaconHelper {
     val redCores = ArrayList<Beacon>()
     val blueCores = ArrayList<Beacon>()
    fun hasTeamCores(team: Team): Boolean = if (team == Team.RED) redCores.size != 0 else blueCores.size != 0
    fun addCoreToTeam(team: Team, beacon: Beacon) {
        if (team == Team.RED) {
            if (!redCores.contains(beacon)) {
                redCores.add(beacon)
            }
        } else {
            if (!blueCores.contains(beacon)) {
                blueCores.add(beacon)
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
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) plugin.gameStateManager.ingameState.isGameOver()
    }
}