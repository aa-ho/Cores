package cores.api

class Cores {

    private val redCores = ArrayList<Beacons>()
    private val blueCores = ArrayList<Beacons>()

    fun addCoreToTeam(team: Teams, beacon: Beacons) {
        if (team == Teams.RED) {
            if (!redCores.contains(beacon)) {
                redCores.add(beacon)
            }
        } else {
            if (!blueCores.contains(beacon)) {
                blueCores.add(beacon)
            }
        }
    }

    fun removeCoreFromTeam(team: Teams, beacon: Beacons) {
        if (team == Teams.RED) {
            if (hasTeamCores(Teams.RED)) {
                redCores.remove(beacon)
            }
        } else if (team == Teams.BLUE) {
            if (hasTeamCores(Teams.BLUE)) {
                blueCores.remove(beacon)
            }
        }
    }

    fun addAllCores() {
        for(i in Beacons.values()) {
            redCores.add(i)
            blueCores.add(i)
        }
    }

    private fun hasTeamCores(team: Teams): Boolean = if (team == Teams.RED) redCores.size != 0 else blueCores.size != 0

}