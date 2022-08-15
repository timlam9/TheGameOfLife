package com.lamti.thegameoflife

import androidx.lifecycle.ViewModel
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.GameEngine
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val gameEngine: GameEngine = GameEngine()) : ViewModel() {

    val board: StateFlow<GameBoard> = gameEngine.board

    fun initBoard(listRange: Int, columns: Int) {
        gameEngine.createRandomBoard(listRange, columns)
    }

    fun updateState() {
        gameEngine.updateState()
    }

    fun restartGame(listRange: Int, columns: Int) {
        gameEngine.createRandomBoard(listRange, columns)
    }

}
