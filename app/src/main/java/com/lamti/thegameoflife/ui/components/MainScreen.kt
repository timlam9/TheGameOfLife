package com.lamti.thegameoflife.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.MainViewModel
import com.lamti.thegameoflife.R
import com.lamti.thegameoflife.domain.Screen
import com.lamti.thegameoflife.ui.theme.BOX_WIDTH
import com.lamti.thegameoflife.ui.theme.PADDING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val cellSizeFlow = MutableStateFlow(BOX_WIDTH)

@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val screenState: Screen = viewModel.screenStateFlow.collectAsState().value

    val coroutineScope = rememberCoroutineScope()
    val cellSize = cellSizeFlow.collectAsState().value

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val columns: Int = (screenWidth / cellSize)
    val listRange: Int = (screenHeight / cellSize) * columns

    LaunchedEffect(Unit) {
        viewModel.initBoard(listRange, columns)
    }

    val board = viewModel.board.collectAsState().value
    viewModel.updateState()

    Box(modifier = Modifier.fillMaxSize()) {
        GameGrid(board, cellSize)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            AnimatedVisibility(visible = screenState == Screen.Game) {
                IconButton(icon = R.drawable.ic_settings) {
                    viewModel.settingsButtonClicked()
                }
            }
            AnimatedVisibility(visible = screenState == Screen.Settings) {
                Settings(
                    onCloseClicked = { viewModel.closeSettingsButtonClicked() },
                    onRestartClicked = { viewModel.restartGame(listRange, columns) },
                    onSliderValueChanged = { value ->
                        coroutineScope.launch(Dispatchers.Default) {
                            cellSizeFlow.update {
                                (value * 80).toInt()
                            }

                            val updatedColumns: Int = (screenWidth / cellSize)
                            val updatedListRange: Int = (screenHeight / cellSize) * columns
                            viewModel.restartGame(updatedListRange, updatedColumns)
                        }
                    }
                )
            }
        }

    }
}
