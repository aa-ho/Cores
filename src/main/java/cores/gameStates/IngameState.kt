package cores.gameStates

import cores.api.GlobalVars.TEAM_BLUE
import cores.api.GlobalVars.TEAM_RED

class IngameState {

    fun isGameOver() {
        if(TEAM_RED.size == 0 || TEAM_BLUE.size == 0) {
            //TODO END STATE
        }
    }

}
