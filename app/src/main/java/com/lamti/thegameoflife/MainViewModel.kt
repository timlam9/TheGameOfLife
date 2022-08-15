package com.lamti.thegameoflife

import androidx.lifecycle.ViewModel
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.GameEngine
import com.lamti.thegameoflife.domain.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val gameEngine: GameEngine = GameEngine()) : ViewModel() {

    val board: StateFlow<GameBoard> = gameEngine.board

    private val _screenStateFlow = MutableStateFlow(Screen.Game)
    val screenStateFlow: StateFlow<Screen> = _screenStateFlow


    fun initBoard(listRange: Int, columns: Int) {
        gameEngine.createRandomBoard(listRange, columns)
    }

    fun updateState() {
        gameEngine.updateState()
    }

    fun restartGame(listRange: Int, columns: Int) {
        gameEngine.createRandomBoard(listRange, columns)
    }

    fun settingsButtonClicked() {
        _screenStateFlow.update { Screen.Settings }
    }

    fun closeSettingsButtonClicked() {
        _screenStateFlow.update { Screen.Game }
    }

}
