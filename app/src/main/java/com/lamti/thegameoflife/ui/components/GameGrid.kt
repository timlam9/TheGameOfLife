package com.lamti.thegameoflife.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.domain.GameBoard

@ExperimentalFoundationApi
@Composable
fun GameGrid(board: GameBoard) {
    val gridState = rememberLazyListState()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(BOX_WIDTH.dp),
        state = gridState,
        contentPadding = PaddingValues(12.dp),
        content = {
            items(board.list.size) { index ->
                GameCell(
                    text = board.list[index].index.toString(),
                    color = board.list[index].status.color
                )
            }
        }
    )
}
