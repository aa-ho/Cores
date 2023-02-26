package cores.api

import cores.Main.Companion.plugin
import cores.api.ImportantFunctions.updateInGameScoreboardAll

class Cores {

    private val redCores = ArrayList<Beacon>()
    private val blueCores = ArrayList<Beacon>()

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
    }

    fun addAllCores() {
        for(i in Beacon.values()) {
            redCores.add(i)
            blueCores.add(i)
        }
    }

    private fun hasTeamCores(team: Team): Boolean = if (team == Team.RED) redCores.size != 0 else blueCores.size != 0

}