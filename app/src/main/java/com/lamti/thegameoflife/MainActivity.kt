package com.lamti.thegameoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lamti.thegameoflife.domain.GameBoard
import com.lamti.thegameoflife.domain.GameEngine
import com.lamti.thegameoflife.ui.components.GameGrid
import com.lamti.thegameoflife.ui.components.MainScreen
import com.lamti.thegameoflife.ui.theme.TheGameOfLifeTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val gameEngine = GameEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheGameOfLifeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(gameEngine)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val gameEngine = GameEngine().apply {
        createRandomBoard(30, 5)
    }
    TheGameOfLifeTheme {
        GameGrid(gameEngine.board.collectAsState().value)
    }
}
