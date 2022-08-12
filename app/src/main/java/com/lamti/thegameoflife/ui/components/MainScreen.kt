package com.lamti.thegameoflife.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.domain.GameEngine

const val BOX_WIDTH = 20

@ExperimentalFoundationApi
@Composable
fun MainScreen(gameEngine: GameEngine) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val columns: Int = (screenWidth / BOX_WIDTH)
    val listRange: Int = (screenHeight / BOX_WIDTH) * columns

    val board = gameEngine.board.collectAsState().value

    LaunchedEffect(Unit) {
        gameEngine.createRandomBoard(listRange, columns)
    }

    LaunchedEffect(board) {
        gameEngine.updateState()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GameGrid(board)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            RestartButton(modifier = Modifier.padding(16.dp)) {
                gameEngine.createRandomBoard(listRange, columns)
            }
        }
    }
}
