package cores.countdown

abstract class Countdown {
    protected var taskID = 69
    var isIdling = false
    abstract fun start()
    abstract fun stop()
}


