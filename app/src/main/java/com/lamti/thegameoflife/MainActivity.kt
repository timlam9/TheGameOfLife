package com.lamti.thegameoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.thegameoflife.ui.theme.TheGameOfLifeTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheGameOfLifeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val screenHeight = LocalConfiguration.current.screenHeightDp
                    val screenWidth = LocalConfiguration.current.screenWidthDp
                    val columns: Int = (screenWidth / 80)
                    val listRange: Int = (screenHeight / 80) * columns

                    var board by remember { mutableStateOf(createBoard(listRange, columns)) }

                    LaunchedEffect(board) {
                        board = board.nextState(board.columns)
                    }

                    GameGrid(board)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd,
                    ) {
                        RestartButton(modifier = Modifier.padding(16.dp)) {
                            board = createBoard(listRange, columns)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RestartButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
    ) {
        Text("Restart", color = Color.DarkGray)
    }
}

@ExperimentalFoundationApi
@Composable
fun GameGrid(board: GameBoard) {
    val gridState = rememberLazyListState()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(80.dp),
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

@Composable
private fun GameCell(
    text: String,
    color: Color = Color.DarkGray,
    textColor: Color = Color.White,
) {
    Card(
        backgroundColor = color,
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

data class GameBox(
    val status: LifeStatus = LifeStatus.Dead,
    val index: Int
)

enum class LifeStatus(val color: Color) {
    Dead(Color.DarkGray),
    Alive(Color.White);

    companion object {

        fun random(): LifeStatus = if ((0..1).random() == 0) Dead else Alive
    }
}

data class GameBoard(
    val list: List<GameBox>,
    val columns: Int
)

fun createBoard(range: Int, columns: Int): GameBoard = GameBoard(
    list = (1..range).map { GameBox(status = LifeStatus.random(), index = it) },
    columns = columns
)

fun GameBoard.nextState(boardColumns: Int): GameBoard {
    val resultList = mutableListOf<GameBox>()
    val neighboursList = mutableListOf<List<GameBox>>()
    val neighboursMap: MutableMap<GameBox, List<GameBox>> = mutableMapOf()

    list.forEach { box ->
        val boxNeighbours = mutableListOf<GameBox>()
        val neighboursInt: List<Int> = box.findNeighbours(boardColumns, list.size)

        neighboursInt.forEach {
            boxNeighbours.add(list[it - 1])
        }

        neighboursList.add(boxNeighbours)
        neighboursMap[box] = boxNeighbours
    }

    neighboursMap.forEach { (gameBox, list) ->
        val nextStateLifeStatus = gameBox.applyRules(list)
        resultList.add(GameBox(status = nextStateLifeStatus, index = gameBox.index))
    }

    return GameBoard(list = resultList, columns = columns)
}

fun GameBox.findNeighbours(columns: Int, listSize: Int): List<Int> {
    val north = if (index - columns > 0) index - columns else 0
    val south = if (index + columns < listSize) index + columns else 0
    val east = if (index.rem(columns) == 0) 0 else index + 1
    val west = if ((index - 1).rem(columns) == 0) 0 else index - 1
    val northWest = if (north == 0) 0 else north - 1
    val northEast = if (north == 0) 0 else north + 1
    val southWest = if (south == 0) 0 else south - 1
    val southEast = if (south == 0) 0 else south + 1

    return listOf(north, south, east, west, northWest, northEast, southWest, southEast).filterNot { it == 0 }
}

fun GameBox.applyRules(neighbours: List<GameBox>): LifeStatus = when {
    firstRule(neighbours) -> LifeStatus.Alive
    secondRule(neighbours) -> LifeStatus.Dead
    else -> status
}

private fun GameBox.firstRule(neighbours: List<GameBox>) = status == LifeStatus.Dead
        && neighbours.count { it.status == LifeStatus.Alive } == 3

private fun GameBox.secondRule(neighbours: List<GameBox>) = status == LifeStatus.Alive
        && neighbours.count { it.status == LifeStatus.Alive } < 2
        || neighbours.count { it.status == LifeStatus.Alive } > 3


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheGameOfLifeTheme {
        GameGrid(createBoard(30, 5))
    }
}
