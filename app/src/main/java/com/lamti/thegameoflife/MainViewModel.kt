package com.lamti.thegameoflife

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.GameEngine
import com.lamti.thegameoflife.domain.Screen
import com.lamti.thegameoflife.ui.theme.BOX_WIDTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val gameEngine: GameEngine = GameEngine()) : ViewModel() {

    val board: StateFlow<GameBoard> = gameEngine.board

    private val _screenStateFlow = MutableStateFlow(Screen.Game)
    val screenStateFlow: StateFlow<Screen> = _screenStateFlow

    private val _cellSizeFlow = MutableStateFlow(BOX_WIDTH)
    val cellSizeFlow: StateFlow<Int> = _cellSizeFlow

    private val _isMatrixThemeOn = MutableStateFlow(false)
    val isMatrixThemeOn: MutableStateFlow<Boolean> = _isMatrixThemeOn

    fun initBoard(listRange: Int, columns: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            gameEngine.createRandomBoard(listRange, columns)
        }
    }

    fun updateState() {
        viewModelScope.launch(Dispatchers.Default) {
            gameEngine.updateState()
        }
    }

    fun restartGame(listRange: Int, columns: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            gameEngine.createRandomBoard(listRange, columns)
        }
    }

    fun settingsButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            _screenStateFlow.update { Screen.Settings }
        }
    }

    fun closeSettingsButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            _screenStateFlow.update { Screen.Game }
        }
    }

    fun onSliderValueChanged(value: Float) {
        viewModelScope.launch(Dispatchers.Default) {
            _cellSizeFlow.update {
                (value * 80).toInt()
            }
        }
    }

    fun matrixThemeClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            _isMatrixThemeOn.update { !it }
        }
    }

}
