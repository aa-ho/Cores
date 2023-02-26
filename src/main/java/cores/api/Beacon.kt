package cores.api

import cores.api.GlobalConst.defaultLocation
import org.bukkit.Location

enum class Beacon {
    Front, Back, Left, Right
}
/*
enum class TeamBeacon(val team: Team, var loc: Location, var alive: Boolean) {
    teamBeacon(Team.RED, defaultLocation, true)
}*/
