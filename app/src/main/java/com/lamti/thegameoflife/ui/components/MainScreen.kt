package com.lamti.thegameoflife.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import com.lamti.thegameoflife.MainViewModel
import com.lamti.thegameoflife.R
import com.lamti.thegameoflife.domain.Screen
import com.lamti.thegameoflife.ui.theme.PADDING

@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val screenState: Screen = viewModel.screenStateFlow.collectAsState().value
    val cellSize = viewModel.cellSizeFlow.collectAsState().value
    val board = viewModel.board.collectAsState().value
    val isMatrixThemeOn = viewModel.isMatrixThemeOn.collectAsState().value

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val columns: Int = (screenWidth / cellSize)
    val listRange: Int = (screenHeight / cellSize) * columns

    LaunchedEffect(Unit) { viewModel.initBoard(listRange, columns) }
    viewModel.updateState()

    Box(modifier = Modifier.fillMaxSize()) {
        GameGrid(board, cellSize, isMatrixThemeOn)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            AnimatedVisibility(visible = screenState == Screen.Game) {
                IconButton(
                    icon = R.drawable.ic_settings,
                    onclick = viewModel::settingsButtonClicked
                )
            }
            AnimatedVisibility(visible = screenState == Screen.Settings) {
                Settings(
                    isMatrixThemeOn = viewModel.isMatrixThemeOn.collectAsState().value,
                    onMatrixThemeClicked = viewModel::matrixThemeClicked,
                    onCloseClicked = viewModel::closeSettingsButtonClicked,
                    onRestartClicked = { viewModel.restartGame(listRange, columns) },
                    onSliderValueChanged = viewModel::onSliderValueChanged
                )
            }
        }
    }
}
