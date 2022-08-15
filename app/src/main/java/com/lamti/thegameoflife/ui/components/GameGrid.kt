package com.lamti.thegameoflife.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.LifeStatus
import com.lamti.thegameoflife.ui.theme.GRID_PADDING

@ExperimentalFoundationApi
@Composable
fun GameGrid(board: GameBoard, cellSize: Int, isMatrixThemeOn: Boolean) {
    val gridState = rememberLazyListState()

    @Composable
    fun getColor(index: Int) = when (board.list[index].status) {
        LifeStatus.Dead -> MaterialTheme.colors.background
        else -> if (isMatrixThemeOn) Green else MaterialTheme.colors.onBackground
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(cellSize.dp),
        state = gridState,
        contentPadding = PaddingValues(GRID_PADDING.dp),
        content = {
            items(board.list.size) { index ->
                GameCell(
                    text = board.list[index].index.toString(),
                    color = getColor(index = index)
                )
            }
        }
    )
}
