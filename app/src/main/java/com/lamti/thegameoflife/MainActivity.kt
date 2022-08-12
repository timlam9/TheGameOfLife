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
import androidx.compose.runtime.collectAsState
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
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.GameEngine
import com.lamti.thegameoflife.ui.theme.TheGameOfLifeTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val gameEngine = GameEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheGameOfLifeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val screenHeight = LocalConfiguration.current.screenHeightDp
                    val screenWidth = LocalConfiguration.current.screenWidthDp

                    val columns: Int = (screenWidth / 80)
                    val listRange: Int = (screenHeight / 80) * columns

                    val board = gameEngine.board.collectAsState().value

                    LaunchedEffect(Unit) {
                        gameEngine.createRandomBoard(listRange, columns)
                    }

                    LaunchedEffect(board) {
                        gameEngine.updateState()
                    }

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




@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheGameOfLifeTheme {
        GameGrid(GameBoard(listOf(), 5))
    }
}
