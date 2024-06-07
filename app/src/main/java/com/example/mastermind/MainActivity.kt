package com.example.mastermind

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermind.data.ColourOptions
import com.example.mastermind.ui.MasterMindViewModel
import com.example.mastermind.ui.theme.MastermindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MastermindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  MainGameScreen()

                }
            }
        }
    }
    
}


@Composable
fun MainGameScreen(
    masterMindViewModel: MasterMindViewModel = viewModel()
){

    val mastermindUiState by masterMindViewModel.uiState.collectAsState()

    val randomSeq = masterMindViewModel.randomSequence


    Log.d("RandomNumStart", randomSeq.contentToString())

    var numGuesses by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {


        // display rows

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
                //.background(Color.Red)
            ) {

                SingleGameRow(
                    imagesBoard = masterMindViewModel.gameBoard[0],
                    imageFeedback = masterMindViewModel.feedBackBoard[0]

                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[1],
                    imageFeedback = masterMindViewModel.feedBackBoard[1]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[2],
                    imageFeedback = masterMindViewModel.feedBackBoard[2]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[3],
                    imageFeedback = masterMindViewModel.feedBackBoard[3]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[4],
                    imageFeedback = masterMindViewModel.feedBackBoard[4]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[5],
                    imageFeedback = masterMindViewModel.feedBackBoard[5]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[6],
                    imageFeedback = masterMindViewModel.feedBackBoard[6]
                )
                SingleGameRow(
                    masterMindViewModel.gameBoard[7],
                    imageFeedback = masterMindViewModel.feedBackBoard[7]
                )


            }


        Spacer(modifier = Modifier.weight(1f))

        //show current guess
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,

            ) {
            // Display user inputs
            Image(
                painter = painterResource(id = masterMindViewModel.imgRes1),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Image(
                painter = painterResource(id = masterMindViewModel.imgRes2),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Image(
                painter = painterResource(id = masterMindViewModel.imgRes3),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Image(
                painter = painterResource(id = masterMindViewModel.imgRes4),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            // End of display user input


        }
        // User Input Area
        //Log.d("Press", newGuess.contentToString())

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    UIColourButton(ColourOptions.BLUE) { masterMindViewModel.onUiColourButtonPressed(ColourOptions.BLUE) }

                    UIColourButton(ColourOptions.GREY){masterMindViewModel.onUiColourButtonPressed(ColourOptions.GREY)}

                    UIColourButton(ColourOptions.GREEN){ masterMindViewModel.onUiColourButtonPressed(ColourOptions.GREEN)}


                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    UIColourButton(ColourOptions.RED){ masterMindViewModel.onUiColourButtonPressed(ColourOptions.RED)}

                    UIColourButton(ColourOptions.ORANGE) { masterMindViewModel.onUiColourButtonPressed(ColourOptions.ORANGE)}

                    UIColourButton(ColourOptions.YELLOW) { masterMindViewModel.onUiColourButtonPressed(ColourOptions.YELLOW)}

                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier//.fillMaxSize()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ios_delete_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .clickable {
                            if (masterMindViewModel.currentPos != 0) masterMindViewModel.currentPos--
                            when (masterMindViewModel.currentPos) {
                                0 -> masterMindViewModel.imgRes1 = R.drawable.white_blob
                                1 -> masterMindViewModel.imgRes2 = R.drawable.white_blob
                                2 -> masterMindViewModel.imgRes3 = R.drawable.white_blob
                                3 -> masterMindViewModel.imgRes4 = R.drawable.white_blob
                            }
                        }
                )

                IconButton(

                    onClick = {
                        if (masterMindViewModel.currentPos == 4) {
                            // check user guess
                            //val feedBackVals = masterMindViewModel.checkUserGuess()
                            //Log.d("feedback vals", feedBackVals.contentToString())

                            val feedBackVals = masterMindViewModel.checkUserGuess(
                                //currentPos = masterMindViewModel.currentPos,
                                //userGuess = masterMindViewModel.userGuess,
                                //randomSeq = randomSeq,
                            )

                            // reset user guess
                            masterMindViewModel.resetUserGuess()


                             // reset current position, so can start next guess
                            numGuesses++
                            Log.d("After user guess reset", masterMindViewModel.userGuess.contentToString())
                            Log.d("RandAfterCheck", randomSeq.contentToString())

                            // UPDATE FEEDBACK BLOBS
                            //masterMindViewModel.updateFeedbackBlobs()

                            val correctInPlace = feedBackVals[0]
                            val correctWrongPlace = feedBackVals[1]

                            // update feedback loops, awful code, works but looks horrid FIX/MAKE LOOK NICE
                            when (correctInPlace) {
                                0 -> {
                                    when (correctWrongPlace) {
                                        1 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.black_blob
                                        }
                                        2 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob

                                        }
                                        3 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob
                                        }
                                        4 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][3] = R.drawable.black_blob
                                        }
                                    }
                                }
                                1 -> {
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.white_blob
                                    when (correctWrongPlace) {
                                        1 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob
                                        }
                                        2 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob

                                        }
                                        3 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][3] = R.drawable.black_blob
                                        }
                                    }
                                }
                                2 -> {
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.white_blob

                                    when (correctWrongPlace) {
                                        1 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob
                                        }

                                        2 -> {
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.black_blob
                                            masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][3] = R.drawable.black_blob
                                        }
                                    }
                                }


                                3 -> {
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.white_blob
                                    if (correctWrongPlace == 1) masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses][3] = R.drawable.black_blob
                                }
                                4 -> {
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][0] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][1] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][2] = R.drawable.white_blob
                                    masterMindViewModel.feedBackBoard[masterMindViewModel.numOfGuesses-1][3] = R.drawable.white_blob

                                }
                            }

                            masterMindViewModel.updateCurrentPos(0)

                            //out++
                        }

                    } // END OF CLICKABLE



                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(50.dp)
                    )
                }

            }
            if (mastermindUiState.isGameOver) {
                FinalScoreDialog(
                    onPlayAgain = {masterMindViewModel.resetGame() },
                    playerWon = mastermindUiState.playerWon,
                    correctSeq = masterMindViewModel.randomSequence

                )
            }
        }
    }

//return out
}


@Composable
fun SingleGameRow(
    imagesBoard: Array<Int>,
    imageFeedback: Array<Int>
    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,

    ) {
        // Display user inputs
        Image(
            painter = painterResource(id = imagesBoard[0]),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(id = imagesBoard[1]),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(id = imagesBoard[2]),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(id = imagesBoard[3]),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        // End of display user input

        //Spacer(modifier = Modifier.weight(1f))

        // Feedback blobs
        Column {
            Row {
                BlankBlobImage(size = 30, imageFeedback[0])
                BlankBlobImage(size = 30, imageFeedback[1])
            }
            Row {
                BlankBlobImage(size = 30, imageFeedback[2])
                BlankBlobImage(size = 30, imageFeedback[3])
            }
        }

    }
}


//@OptIn(ExperimentalMaterial3Api::class)

@Composable
private fun FinalScoreDialog(
    //score: Int,
    onPlayAgain: () -> Unit,
    playerWon: Boolean,
    correctSeq: Array<ColourOptions>,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { if (playerWon) Text(text = "You Won!") else Text(text = "You Lost")},
        text = { Text(text = "The correct sequence was\n ${correctSeq.contentToString()}", fontSize = 20.sp)},
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "quit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play again")
            }
        }
    )
}



@Composable
fun UIColourButton(
    colour: ColourOptions,
    onClick: () -> Unit
) {
    val imgRes = when (colour){
        ColourOptions.BLUE   -> {R.drawable.blue}
        ColourOptions.GREY   -> {R.drawable.grey}
        ColourOptions.GREEN  -> {R.drawable.green}
        ColourOptions.RED    -> {R.drawable.red }
        ColourOptions.ORANGE -> {R.drawable.orange}
        ColourOptions.YELLOW -> {R.drawable.yellow}
        else -> {R.drawable.black_blob}
    }
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            ),
        modifier = Modifier
            .size(100.dp)

        ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = null,
            modifier = Modifier
                //.padding(start = 8.dp)
                .size(100.dp)
                .fillMaxSize()
        )
    }
}


@Composable
fun BlankBlobImage(
    size: Int,
    imgRes: Int
) {
    Image(
        painter = painterResource(id = imgRes),
        contentDescription = null,
        modifier = Modifier.size(size.dp)
    )
}


@Preview
@Composable
fun MainGameScreenPreview(){
    MainGameScreen()
}