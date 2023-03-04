package cores.api

import cores.Main.Companion.plugin
import cores.api.ImportantFunctions.updateInGameScoreboardAll
import cores.gameStates.GameStates

class Cores {
    private val redCores = ArrayList<Beacon>()
    private val blueCores = ArrayList<Beacon>()
    private fun hasTeamCores(team: Team): Boolean = if (team == Team.RED) redCores.size != 0 else blueCores.size != 0
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
            if (hasTeamCores(Team.RED)) {
                redCores.remove(beacon)
            }
        } else if (team == Team.BLUE) {
            if (hasTeamCores(Team.BLUE)) {
                blueCores.remove(beacon)
            }
        }
        updateInGameScoreboardAll()
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.INGAME_STATE) plugin.gameStateManager.ingameState.isGameOver()
    }

    fun addAllCores() {
        for (i in Beacon.values()) {
            redCores.add(i)
            blueCores.add(i)
        }
    }
}