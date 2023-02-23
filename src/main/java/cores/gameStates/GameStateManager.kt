package cores.gameStates

class GameStateManager {

    private var current_game_state = GameStates.LOBBY_STATE

    fun getCurrentGameState(): GameStates = current_game_state

    val lobbyState = LobbyState()
    val ingameState = IngameState()
    val endState = EndState()

    fun startGameState(gameState: GameStates) {
        //TODO unschön
        when (gameState) {
            GameStates.LOBBY_STATE -> {
                lobbyState.start()
            }
            GameStates.INGAME_STATE -> {
                ingameState.start()
            }
            GameStates.END_STATE -> {
                endState.start()
            }
        }
    }

    fun stopGameState(gameState: GameStates) {
        //TODO unschön
        when (gameState) {
            GameStates.LOBBY_STATE -> {
                lobbyState.stop()
            }
            GameStates.INGAME_STATE -> {
                ingameState.stop()
            }
            GameStates.END_STATE -> {
                endState.stop()
            }
        }
    }

    fun setGameState(gameState: GameStates) {
        stopGameState(current_game_state)
        current_game_state = gameState
        startGameState(current_game_state)
    }
}