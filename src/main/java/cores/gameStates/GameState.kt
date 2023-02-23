package cores.gameStates

abstract class GameState {
    protected var taskID = 99
    var isRunning = false
    abstract fun start()
    abstract fun stop()
}